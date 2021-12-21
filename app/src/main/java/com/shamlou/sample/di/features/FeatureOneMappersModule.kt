package com.shamlou.sample.di.features

import com.shamlou.bases.mapper.Mapper
import com.shamlou.bases.useCase.FlowUseCase
import com.shamlou.data.mappers.posts.MapperPostDataToDomain
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_local.mappers.MapperPostDataToLocal
import com.shamlou.data_local.mappers.MapperPostLocalToData
import com.shamlou.data_local.model.posts.ResponsePostLocal
import com.shamlou.data_remote.mappers.MapperPostRemoteToData
import com.shamlou.data_remote.model.posts.ResponsePostRemote
import com.shamlou.domain.model.posts.ResponsePostDomain
import com.shamlou.domain.model.posts.ResponsePostsDomain
import com.shamlou.domain.usecases.posts.GetPostsUseCase
import com.shamlou.featureone.di.FeatureOneScope
import com.shamlou.featureone.mappers.posts.MapperPostDomainToView
import com.shamlou.featureone.model.posts.ResponsePostsView
import dagger.Binds
import dagger.Module

@Module
abstract class FeatureOneMappersModule {

    @Binds
    @FeatureOneScope
    abstract fun bindMapperPostDataToLocal(mapper: MapperPostDataToLocal): Mapper<ResponsePostData, ResponsePostLocal>

    @Binds
    @FeatureOneScope
    abstract fun bindMapperPostLocalToData(mapper: MapperPostLocalToData): Mapper<ResponsePostLocal, ResponsePostData>

    @Binds
    @FeatureOneScope
    abstract fun bindMapperPostRemoteToData(mapper: MapperPostRemoteToData): Mapper<ResponsePostRemote, ResponsePostData>

    @Binds
    @FeatureOneScope
    abstract fun bindMapperPostDataToDomain(mapper: MapperPostDataToDomain): Mapper<ResponsePostData, ResponsePostDomain>

    @Binds
    @FeatureOneScope
    abstract fun bindMapperPostDomainToView(mapper: MapperPostDomainToView): Mapper<ResponsePostsDomain, ResponsePostsView>

}