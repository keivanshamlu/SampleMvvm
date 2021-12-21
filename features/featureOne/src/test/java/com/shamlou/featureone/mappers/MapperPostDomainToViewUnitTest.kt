package com.shamlou.featureone.mappers

import com.shamlou.bases.mapper.Mapper
import com.shamlou.domain.model.posts.ResponsePostsDomain
import com.shamlou.featureone.MainCoroutineRule
import com.shamlou.featureone.ValidGetPostsResponse.validResponsePostsDomain
import com.shamlou.featureone.ValidGetPostsResponse.validResponsePostsView
import com.shamlou.featureone.mappers.posts.MapperPostDomainToView
import com.shamlou.featureone.model.posts.ResponsePostsView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class MapperPostDomainToViewUnitTest(){

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mapper : Mapper<ResponsePostsDomain, ResponsePostsView> = MapperPostDomainToView()

    @Test
    @ExperimentalCoroutinesApi
    fun map_shouldReturnCompatibleResponseWhenValidIput() = mainCoroutineRule.runBlockingTest {
        //when
        val response = mapper.map(ResponsePostsDomain(validResponsePostsDomain))
        //then
        Assert.assertEquals(response , ResponsePostsView(validResponsePostsView))
    }
}