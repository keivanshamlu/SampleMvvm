package com.shamlou.data_remote.mappers

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_remote.model.posts.ResponsePostRemote
import com.shamlou.data_remote.utility.CompatibleResponsePost.compatibalePostData
import com.shamlou.data_remote.utility.CompatibleResponsePost.compatibalePostDataEmpty
import com.shamlou.data_remote.utility.CompatibleResponsePost.compatibalePostRemote
import com.shamlou.data_remote.utility.CompatibleResponsePost.compatibalePostRemoteNull
import com.shamlou.data_remote.utility.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MapperPostRemoteToDataUnitTest(){

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mapper : Mapper<ResponsePostRemote, ResponsePostData> = MapperPostRemoteToData()

    @Test
    @ExperimentalCoroutinesApi
    fun map_shouldReturnCompatibleResponseWhenValidIput() = mainCoroutineRule.runBlockingTest {
        //when
        val response = mapper.map(compatibalePostRemote)
        //then
        Assert.assertEquals(response , compatibalePostData)
    }
    @Test
    @ExperimentalCoroutinesApi
    fun map_shouldReturnEmptyResponseWhenNullInput() = mainCoroutineRule.runBlockingTest {
        //when
        val response = mapper.map(compatibalePostRemoteNull)
        //then
        Assert.assertEquals(response , compatibalePostDataEmpty)
    }
}