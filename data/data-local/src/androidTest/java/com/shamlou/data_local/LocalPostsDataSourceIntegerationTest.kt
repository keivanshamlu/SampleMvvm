package com.shamlou.data_local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data.model.posts.ResponsePostsData
import com.shamlou.data_local.dataSources.posts.LocalPostsDataSource
import com.shamlou.data_local.db.posts.PostsDataBase
import com.shamlou.data_local.db.posts.daos.PostsDao
import com.shamlou.data_local.model.posts.ResponsePostLocal
import com.shamlou.data_local.utility.MainCoroutineRule
import com.shamlou.data_local.utility.ValidGetPostsResponse.validResponsePostsData
import com.shamlou.data_local.utility.ValidGetPostsResponse.validResponsePostsLocal
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

class LocalPostsDataSourceIntegerationTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var dataSource: LocalPostsDataSource

    @MockK
    lateinit var mapperDataToLocal: Mapper<ResponsePostData, ResponsePostLocal>
    @MockK
    lateinit var mapperLocalToData: Mapper<ResponsePostLocal, ResponsePostData>

    private lateinit var dataBase : PostsDataBase
    lateinit var postsDao : PostsDao

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        dataBase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            PostsDataBase::class.java
        ).build()
        postsDao = dataBase.getPostsDao()
        dataSource = LocalPostsDataSource(postsDao, mapperDataToLocal, mapperLocalToData)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun write_shouldMappAndSaveItemsOnDataBase() = mainCoroutineRule.runBlockingTest {
        //given
        mappersDoingTheirJob()
        //when
        dataSource.write(ResponsePostsData(validResponsePostsData))
        //then
        val allLocalPosts = dataSource.read(Unit)
        Assert.assertEquals(allLocalPosts , ResponsePostsData(validResponsePostsData))
    }
    @Test
    @ExperimentalCoroutinesApi
    fun write_shouldReplaceDuplicateItemsOnDataBase() = mainCoroutineRule.runBlockingTest {
        //given
        mappersDoingTheirJob()
        //when
        dataSource.write(ResponsePostsData(validResponsePostsData))
        dataSource.write(ResponsePostsData(listOf(validResponsePostsData.first())))
        dataSource.write(ResponsePostsData(listOf(validResponsePostsData.last())))
        //then
        val allLocalPosts = dataSource.read(Unit)
        Assert.assertEquals(allLocalPosts , ResponsePostsData(validResponsePostsData))
    }

    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldRespondEmptyWhenDBisEmpty() = mainCoroutineRule.runBlockingTest {
        //given
        mappersDoingTheirJob()
        //when
        val allLocalPosts = dataSource.read(Unit)
        //then
        Assert.assertEquals(allLocalPosts , ResponsePostsData(emptyList()))
    }

    @After
    fun tearDown(){

        dataBase.close()
    }

    private fun mappersDoingTheirJob(){
        validResponsePostsData.mapIndexed { index, item ->
            every { mapperDataToLocal.map(item) } returns validResponsePostsLocal[index]
        }
        validResponsePostsLocal.mapIndexed { index, item ->
            every { mapperLocalToData.map(item) } returns validResponsePostsData[index]
        }
    }
}