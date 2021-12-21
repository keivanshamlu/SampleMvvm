package com.shamlou.data.mappers.posts

import com.shamlou.bases.mapper.Mapper
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.domain.model.posts.ResponsePostDomain
import javax.inject.Inject

class MapperPostDataToDomain@Inject constructor(): Mapper<ResponsePostData, ResponsePostDomain> {
    override fun map(first: ResponsePostData): ResponsePostDomain = first.run { ResponsePostDomain(id, userId, title, body) }
}