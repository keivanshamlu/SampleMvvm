package com.shamlou.data_local.mappers

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_local.model.posts.ResponsePostLocal
import com.shamlou.data_local.utility.CompatibleResponsePost
import com.shamlou.data_local.utility.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MapperPostLocalToDataTest{

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mapper : Mapper<ResponsePostLocal, ResponsePostData> = MapperPostLocalToData()

    @Test
    @ExperimentalCoroutinesApi
    fun map_shouldReturnCompatibleResponseWhenValidIput() = mainCoroutineRule.runBlockingTest {
        //when
        val response = mapper.map(CompatibleResponsePost.compatibalePostLocal)
        //then
        Assert.assertEquals(response , CompatibleResponsePost.compatibalePostData)
    }
}