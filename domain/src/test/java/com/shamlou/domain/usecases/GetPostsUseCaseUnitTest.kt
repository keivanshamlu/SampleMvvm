package com.shamlou.domain.usecases

import com.shamlou.bases.useCase.Resource
import com.shamlou.domain.model.posts.ResponsePostsDomain
import com.shamlou.domain.repo.PostsRepository
import com.shamlou.domain.usecases.posts.GetPostsUseCase
import com.shamlou.domain.utility.MainCoroutineRule
import com.shamlou.domain.utility.PostRemoteFakes.exception
import com.shamlou.domain.utility.PostRemoteFakes.listSampleResponsePostDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetPostsUseCaseUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var useCase : GetPostsUseCase

    @MockK
    lateinit var repository: PostsRepository

    @Before
    fun setUp(){

        MockKAnnotations.init(this)
        useCase = GetPostsUseCase(repository)
    }
    @Test
    @ExperimentalCoroutinesApi
    fun execute_shouldCallGetAllPostsAndReturnPostsAsResources_whenSuccessful() {

        runBlocking {
            //given
            coEvery { repository.getAllPosts(Unit) } returns flow { emit(ResponsePostsDomain(listSampleResponsePostDomain)) }
            //when
            val response = useCase.invoke(Unit).first()
            //then
            coEvery { repository.getAllPosts(Unit) }
            Assert.assertEquals(response, Resource.success(ResponsePostsDomain(listSampleResponsePostDomain)))
        }
    }
    @Test
    @ExperimentalCoroutinesApi
    fun execute_shouldCallGetAllPostsAndReturnErrorAsResources_whenErrorThrows() {

        runBlocking {
            //given
            coEvery { repository.getAllPosts(Unit) } throws exception
            //when
            val response = useCase.invoke(Unit).first()
            //then
            coEvery { repository.getAllPosts(Unit) }
            Assert.assertEquals(response, Resource.error(exception , null))
        }
    }
}