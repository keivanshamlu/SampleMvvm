package com.shamlou.data_remote.dataSources

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data.model.posts.ResponsePostsData
import com.shamlou.data_remote.utility.PostRemoteFakes.compatibleListRemoteData
import com.shamlou.data_remote.utility.PostRemoteFakes.exception
import com.shamlou.data_remote.utility.PostRemoteFakes.listSampleResponsePostRemote
import com.shamlou.data_remote.utility.PostRemoteFakes.sampleResponsePostData
import com.shamlou.data_remote.model.posts.ResponsePostRemote
import com.shamlou.data_remote.services.posts.PostsService
import com.shamlou.data_remote.utility.MainCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RemotePostsDataSourceUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var dataSource: RemotePostsDataSource

    @MockK
    lateinit var mediaSetService: PostsService

    @MockK
    lateinit var mapperDataToLocal: Mapper<ResponsePostRemote, ResponsePostData>

    @Before
    fun setUp() {

        MockKAnnotations.init(this)
        dataSource = RemotePostsDataSource(mediaSetService, mapperDataToLocal)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldCallGetPosts() = mainCoroutineRule.runBlockingTest {

        //given
        coEvery { mediaSetService.getPosts() } returns listOf()
        //when
        dataSource.read(Unit)
        //then
        coVerify { mediaSetService.getPosts() }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldMapResponse() = mainCoroutineRule.runBlockingTest {

        //given
        every { mapperDataToLocal.map(any()) } returns sampleResponsePostData
        coEvery { mediaSetService.getPosts() } returns listSampleResponsePostRemote
        //when
        dataSource.read(Unit)
        //then
        coVerifyOrder {
            listSampleResponsePostRemote.map {
                mapperDataToLocal.map(it)
            }
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldMapResponseAndReturnValidMappedResponse() = mainCoroutineRule.runBlockingTest {

        //given
        compatibleListRemoteData.first.mapIndexed { index, item ->
            every { mapperDataToLocal.map(item) } returns compatibleListRemoteData.second[index]
        }
        coEvery { mediaSetService.getPosts() } returns compatibleListRemoteData.first
        //when
        val respose = dataSource.read(Unit)
        //then
        coVerifyOrder {
            compatibleListRemoteData.first.map {
                mapperDataToLocal.map(it)
            }
        }
        Assert.assertEquals(respose, ResponsePostsData(compatibleListRemoteData.second))
    }

    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldThrowException_whenGetPostsThrowsException() =
        mainCoroutineRule.runBlockingTest {

            //given
            coEvery { mediaSetService.getPosts() } throws exception
            try {
                //when
                dataSource.read(Unit)
            } catch (e: Exception) {
                //then
                Assert.assertEquals(e.javaClass, exception.javaClass)
                Assert.assertEquals(e.message, exception.message)
            }
        }
}