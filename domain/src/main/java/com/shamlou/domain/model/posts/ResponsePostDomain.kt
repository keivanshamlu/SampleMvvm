package com.shamlou.domain.model.posts

data class ResponsePostsDomain(
    val posts : List<ResponsePostDomain>
)
data class ResponsePostDomain(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)