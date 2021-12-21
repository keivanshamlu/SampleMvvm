package com.shamlou.data_remote.dataSources

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data.model.posts.ResponsePostsData
import com.shamlou.data_remote.utility.PostRemoteFakes.sampleResponsePostData
import com.shamlou.data_remote.model.posts.ResponsePostRemote
import com.shamlou.data_remote.services.posts.PostsService
import com.shamlou.data_remote.utility.MainCoroutineRule
import com.shamlou.data_remote.utility.ValidGetPostsResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.EOFException

class RemotePostsDataSourceIntegrationTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mockWebServer = MockWebServer()

    private val service = Retrofit.Builder()
        .client(OkHttpClient.Builder().build())
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PostsService::class.java)

    private lateinit var dataSource: RemotePostsDataSource

    @MockK
    lateinit var mapperDataToLocal: Mapper<ResponsePostRemote, ResponsePostData>

    @Before
    fun setUp() {

        MockKAnnotations.init(this)
        dataSource = RemotePostsDataSource(service, mapperDataToLocal)
        mockWebServer.start(8080)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldGetValidDataWhenServerRespondSuccessfully() {
        //given
        mockWebServer.enqueue(
            MockResponse().setBody(ValidGetPostsResponse.validGetPostsResponse).setResponseCode(200)
        )
        runBlocking {

            ValidGetPostsResponse.validResponsePostsRemote.mapIndexed { index, item ->
                every { mapperDataToLocal.map(item) } returns ValidGetPostsResponse.validResponsePostsData[index]
            }
            //when
            val response = dataSource.read(Unit)

            //then
            verify {
                ValidGetPostsResponse.validResponsePostsRemote.map {
                    mapperDataToLocal.map(it)
                }
            }
            Assert.assertEquals(
                response,
                ResponsePostsData(ValidGetPostsResponse.validResponsePostsData)
            )
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldThrowExceptionWhenServerRespondError() {
        //given
        mockWebServer.enqueue(
            MockResponse().setHttp2ErrorCode(1003)
        )
        runBlocking {
            ValidGetPostsResponse.validResponsePostsRemote.mapIndexed { index, item ->
                every { mapperDataToLocal.map(item) } returns ValidGetPostsResponse.validResponsePostsData[index]
            }
            try {
                //when
                dataSource.read(Unit)
            } catch (e: Exception) {
                //then
                Assert.assertEquals(e.javaClass, EOFException::class.java)
            }
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun regressionTest(){
        //given
        mockWebServer.enqueue(
            MockResponse().setBody(ValidGetPostsResponse.validGetPostsResponse).setResponseCode(200)
        )
        runBlocking {
            every { mapperDataToLocal.map(any()) } returns sampleResponsePostData
            dataSource.read(Unit)
            Assert.assertEquals(mockWebServer.requestCount , 1)
            mockWebServer.takeRequest().apply {
                Assert.assertEquals(method , "GET")
                Assert.assertEquals(path , "/posts")
            }
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}