package com.shamlou.data_local.dataSources

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data.model.posts.ResponsePostsData
import com.shamlou.data_local.dataSources.posts.LocalPostsDataSource
import com.shamlou.data_local.db.posts.daos.PostsDao
import com.shamlou.data_local.model.posts.ResponsePostLocal
import com.shamlou.data_local.utility.MainCoroutineRule
import com.shamlou.data_local.utility.PostRemoteFakes.compatibleListRemoteData
import com.shamlou.data_local.utility.PostRemoteFakes.listSampleResponsePostLocal
import com.shamlou.data_local.utility.PostRemoteFakes.sampleResponsePostData
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocalPostsDataSourceUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var dataSource: LocalPostsDataSource

    @MockK(relaxed = true)
    lateinit var postsDao: PostsDao
    @MockK
    lateinit var mapperDataToLocal: Mapper<ResponsePostData, ResponsePostLocal>
    @MockK
    lateinit var mapperLocalToData: Mapper<ResponsePostLocal, ResponsePostData>

    @Before
    fun setUp() {

        MockKAnnotations.init(this)
        dataSource = LocalPostsDataSource(postsDao, mapperDataToLocal, mapperLocalToData)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldCallGetAllPosts() = mainCoroutineRule.runBlockingTest {
        //given
        coEvery { postsDao.getAllPosts() } returns listOf()
        //when
        dataSource.read(Unit)
        //then
        coVerify { postsDao.getAllPosts() }
    }
    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldCallMapOnAllItems() = mainCoroutineRule.runBlockingTest {
        //given
        every { mapperLocalToData.map(any()) } returns sampleResponsePostData
        coEvery { postsDao.getAllPosts() } returns listSampleResponsePostLocal
        //when
        dataSource.read(Unit)
        //then
        coVerifyOrder {
            listSampleResponsePostLocal.map {
                mapperLocalToData.map(it)
            }
        }
    }
    @Test
    @ExperimentalCoroutinesApi
    fun read_shouldMapResponseAndReturnValidMappedResponse() = mainCoroutineRule.runBlockingTest {
        //given
        compatibleListRemoteData.first.mapIndexed { index, item ->
            every { mapperLocalToData.map(item) } returns compatibleListRemoteData.second[index]
        }
        coEvery { postsDao.getAllPosts() } returns compatibleListRemoteData.first
        //when
        val respose = dataSource.read(Unit)
        //then
        coVerifyOrder {
            compatibleListRemoteData.first.map {
                mapperLocalToData.map(it)
            }
        }
        Assert.assertEquals(respose, ResponsePostsData(compatibleListRemoteData.second))
    }

    @Test
    @ExperimentalCoroutinesApi
    fun write_shouldCallGetAllPosts() = mainCoroutineRule.runBlockingTest {
        //given
        compatibleListRemoteData.second.mapIndexed { index, item ->
            every { mapperDataToLocal.map(item) } returns compatibleListRemoteData.first[index]
        }
        //when
        dataSource.write(ResponsePostsData(compatibleListRemoteData.second))
        //then
        coVerifyOrder {
            compatibleListRemoteData.second.map {
                mapperDataToLocal.map(it)
            }
            postsDao.insertAll(eq(compatibleListRemoteData.first))
        }
    }

}