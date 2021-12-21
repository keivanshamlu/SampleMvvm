package com.shamlou.data_local.mappers

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_local.model.posts.ResponsePostLocal
import javax.inject.Inject

class MapperPostDataToLocal@Inject constructor(): Mapper<ResponsePostData, ResponsePostLocal> {
    override fun map(first: ResponsePostData): ResponsePostLocal = first.run { ResponsePostLocal(id, userId, title, body) }
}