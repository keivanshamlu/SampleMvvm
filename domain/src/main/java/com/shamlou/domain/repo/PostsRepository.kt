package com.shamlou.domain.repo

import com.shamlou.domain.model.posts.ResponsePostsDomain
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    fun getAllPosts(param : Unit): Flow<ResponsePostsDomain>
    fun removeAll(): Flow<Unit>
}