package com.shamlou.data_local.mappers

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_local.model.posts.ResponsePostLocal
import javax.inject.Inject


class MapperPostLocalToData@Inject constructor(): Mapper<ResponsePostLocal, ResponsePostData> {
    override fun map(first: ResponsePostLocal): ResponsePostData = first.run { ResponsePostData(id, userId, title, body) }
}
