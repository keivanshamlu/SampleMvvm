package com.shamlou.domain.usecases.posts

import com.shamlou.bases.useCase.FlowUseCase
import com.shamlou.bases.useCase.Resource
import com.shamlou.domain.model.posts.ResponsePostsDomain
import com.shamlou.domain.repo.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostsRepository
) : FlowUseCase<Unit, ResponsePostsDomain> {

    override fun execute(parameters: Unit): kotlinx.coroutines.flow.Flow<Resource<ResponsePostsDomain>> {
        return repository.getAllPosts(parameters).map {
            Resource.success(it)
        }
    }
}