package com.shamlou.featureone.mappers.posts

import com.shamlou.bases.mapper.Mapper
import com.shamlou.domain.model.posts.ResponsePostsDomain
import com.shamlou.featureone.model.posts.ResponsePostView
import com.shamlou.featureone.model.posts.ResponsePostsView
import javax.inject.Inject


class MapperPostDomainToView@Inject constructor(): Mapper<ResponsePostsDomain, ResponsePostsView> {
    override fun map(first: ResponsePostsDomain): ResponsePostsView = first.posts.map { ResponsePostView(it.id, it.userId, it.title, it.body) }.let { ResponsePostsView(it) }
}