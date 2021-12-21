package com.shamlou.data_remote.mappers

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_remote.model.posts.ResponsePostRemote
import javax.inject.Inject


class MapperPostRemoteToData@Inject constructor(): Mapper<ResponsePostRemote, ResponsePostData> {
    override fun map(first: ResponsePostRemote): ResponsePostData = first.run { ResponsePostData(id?:-1, userId?:-1, title?:"", body?:"") }
}