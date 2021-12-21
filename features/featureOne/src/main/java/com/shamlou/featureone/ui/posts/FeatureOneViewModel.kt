package com.shamlou.featureone.ui.posts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shamlou.bases.mapper.Mapper
import com.shamlou.bases.useCase.FlowUseCase
import com.shamlou.bases.useCase.Resource
import com.shamlou.bases.useCase.map
import com.shamlou.core.assisted.AssistedSavedStateViewModelFactory
import com.shamlou.domain.model.posts.ResponsePostsDomain
import com.shamlou.featureone.model.posts.ResponsePostsView
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FeatureOneViewModel @AssistedInject constructor(@Assisted private val savedStateHandle: SavedStateHandle,
                                                      private val getPostsUseCase : FlowUseCase<Unit, ResponsePostsDomain>,
                                                      private val mapperPostDomainToView : Mapper<ResponsePostsDomain, ResponsePostsView>
) : ViewModel() {

    private val _posts = MutableStateFlow<Resource<ResponsePostsView>>(Resource.loading())
    val posts: StateFlow<Resource<ResponsePostsView>>
        get() = _posts

    init {

        getPosts()
    }
    private fun getPosts() {
        viewModelScope.launch {
            _posts.emit(Resource.loading())
            getPostsUseCase.invoke(Unit).map {
                it.map(mapperPostDomainToView)
            }.collect {
                _posts.emit(it)
            }
        }
    }

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<FeatureOneViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): FeatureOneViewModel
    }
}