package com.shamlou.data.model.posts

data class ResponsePostsData(
    val posts : List<ResponsePostData>
)
data class ResponsePostData(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)