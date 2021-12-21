package com.shamlou.data_remote.model.posts

import com.google.gson.annotations.SerializedName

data class ResponsePostRemote(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("userId")
    var userId: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("body")
    var body: String? = null,
)