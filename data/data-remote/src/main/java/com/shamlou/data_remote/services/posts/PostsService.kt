package com.shamlou.data_remote.services.posts

import com.shamlou.data_remote.model.posts.ResponsePostRemote
import retrofit2.http.GET

interface PostsService {

    @GET("/posts")
    suspend fun getPosts(): List<ResponsePostRemote>
}