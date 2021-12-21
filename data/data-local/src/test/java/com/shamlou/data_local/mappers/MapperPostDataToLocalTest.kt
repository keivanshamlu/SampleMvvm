package com.shamlou.data_local.mappers

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_local.model.posts.ResponsePostLocal
import com.shamlou.data_local.utility.CompatibleResponsePost.compatibalePostData
import com.shamlou.data_local.utility.CompatibleResponsePost.compatibalePostLocal
import com.shamlou.data_local.utility.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MapperPostDataToLocalTest{

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mapper : Mapper<ResponsePostData, ResponsePostLocal> = MapperPostDataToLocal()

    @Test
    @ExperimentalCoroutinesApi
    fun map_shouldReturnCompatibleResponseWhenValidIput() = mainCoroutineRule.runBlockingTest {
        //when
        val response = mapper.map(compatibalePostData)
        //then
        Assert.assertEquals(response , compatibalePostLocal)
    }
}