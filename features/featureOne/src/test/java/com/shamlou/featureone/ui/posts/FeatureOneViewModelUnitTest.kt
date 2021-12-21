package com.shamlou.featureone.ui.posts

import androidx.lifecycle.SavedStateHandle
import com.shamlou.bases.mapper.Mapper
import com.shamlou.bases.useCase.FlowUseCase
import com.shamlou.bases.useCase.Resource
import com.shamlou.bases.useCase.map
import com.shamlou.domain.model.posts.ResponsePostsDomain
import com.shamlou.featureone.MainCoroutineRule
import com.shamlou.featureone.PostRemoteFakes.exception
import com.shamlou.featureone.ValidGetPostsResponse.validResponsePostsDomain
import com.shamlou.featureone.ValidGetPostsResponse.validResponsePostsView
import com.shamlou.featureone.model.posts.ResponsePostsView
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

const val RESOURCES_PACKAGE = "com.shamlou.bases.useCase.ResourceKt"

class FeatureOneViewModelUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: FeatureOneViewModel

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    @MockK
    lateinit var useCasePosts: FlowUseCase<Unit, ResponsePostsDomain>

    @MockK(relaxed = true)
    lateinit var mapperPostDomainToView: Mapper<ResponsePostsDomain, ResponsePostsView>

    //fakes
    private var getPostsUseCaseSuccess = FakeUseCaseSuccess()
    private var getPostsUseCaseFailure = FakeUseCaseFailure()

    @Before
    fun setUp() {

        MockKAnnotations.init(this)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun loading_shouldBeSet_whenViewModelCreate() = mainCoroutineRule.runBlockingTest {


        viewModel = FeatureOneViewModel(savedStateHandle, useCasePosts, mapperPostDomainToView)
        //when
        //viewModel created (already created in setUp())
        //then
        Assert.assertEquals(viewModel.posts.value.status, Resource.Status.LOADING)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getPosts_shouldBeCalledSuccess_whenUseCaseGetSuccessful() =
        mainCoroutineRule.dispatcher.runBlockingTest {

            //given
            viewModel = FeatureOneViewModel(
                savedStateHandle,
                getPostsUseCaseSuccess,
                mapperPostDomainToView
            )
            val responseDomain = ResponsePostsDomain(validResponsePostsDomain)
            val responseView = ResponsePostsView(validResponsePostsView)

            //when
            //viewModel created (already created in setUp())
            mockkStatic(RESOURCES_PACKAGE)//mockk way to stub Extension functions
            every { mapperPostDomainToView.map(responseDomain) } returns responseView
            every {
                Resource(Resource.Status.SUCCESS, responseDomain, null).map(
                    mapperPostDomainToView
                )
            } returns Resource.success(responseView)

            //then
            val emited = mutableListOf<Resource<ResponsePostsView>>()
            val job = launch {
                viewModel.posts.toList(emited)
            }
            job.cancel()
            Assert.assertEquals(emited.last().status, Resource.Status.SUCCESS)
        }

    @Test
    @ExperimentalCoroutinesApi
    fun getPosts_shouldBeCalledError_whenUseCaseGetError() =
        mainCoroutineRule.dispatcher.runBlockingTest {

            //given
            viewModel = FeatureOneViewModel(
                savedStateHandle,
                getPostsUseCaseFailure,
                mapperPostDomainToView
            )
            val responseDomain = ResponsePostsDomain(validResponsePostsDomain)
            val responseView = ResponsePostsView(validResponsePostsView)

            //when
            //viewModel created (already created in setUp())
            mockkStatic(RESOURCES_PACKAGE)//mockk way to stub Extension functions
            every { mapperPostDomainToView.map(responseDomain) } returns responseView
            every {
                Resource(Resource.Status.SUCCESS, responseDomain, null).map(
                    mapperPostDomainToView
                )
            } returns Resource.error(exception)
            //viewModel created (already created in setUp())

            //then
            val emited = mutableListOf<Resource<ResponsePostsView>>()
            val job = launch {
                viewModel.posts.toList(emited)
            }
            job.cancel()
            Assert.assertEquals(emited.last().status, Resource.Status.ERROR)
        }
}

//since mockk does not support stubbing , i created these 2 fake classes so i can stub invoke()
class FakeUseCaseSuccess : FlowUseCase<Unit, ResponsePostsDomain> {
    private val responseDomain = ResponsePostsDomain(validResponsePostsDomain)
    override fun execute(parameters: Unit): Flow<Resource<ResponsePostsDomain>> =
        flow { emit(Resource.success(responseDomain)) }

    override fun invoke(parameters: Unit): Flow<Resource<ResponsePostsDomain>> =
        flow { emit(Resource.success(responseDomain)) }

}

class FakeUseCaseFailure : FlowUseCase<Unit, ResponsePostsDomain> {
    override fun execute(parameters: Unit): Flow<Resource<ResponsePostsDomain>> =
        flow { emit(Resource.error(exception)) }

    override fun invoke(parameters: Unit): Flow<Resource<ResponsePostsDomain>> =
        flow { emit(Resource.error(exception)) }

}