package com.shamlou.data_remote.dataSources

import com.shamlou.bases.mapper.Mapper
import javax.inject.Inject
import com.shamlou.bases.readWrite.Readable
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data.model.posts.ResponsePostsData
import com.shamlou.data_remote.services.posts.PostsService
import com.shamlou.data_remote.model.posts.ResponsePostRemote

class RemotePostsDataSource @Inject constructor(
    private val mediaSetService: PostsService,
    private val mapperDataToLocal: Mapper<ResponsePostRemote, ResponsePostData>
) : Readable<Unit, ResponsePostsData> {
    override suspend fun read(input: Unit): ResponsePostsData {

        return mediaSetService.getPosts().map { mapperDataToLocal.map(it) }.let { ResponsePostsData(it) }
    }
}

