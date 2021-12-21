package com.shamlou.sample.di.features

import com.shamlou.bases.readWrite.Readable
import com.shamlou.bases.readWrite.Writable
import com.shamlou.bases.useCase.FlowUseCase
import com.shamlou.core.assisted.InjectingSavedStateViewModelFactory
import com.shamlou.core.assisted.ViewModelFactory
import com.shamlou.data.read_write.posts.LocalReadable
import com.shamlou.data.repoImpl.PostsRepositoryImpl
import com.shamlou.data.read_write.posts.RemoteReadable
import com.shamlou.data.model.posts.ResponsePostsData
import com.shamlou.data_local.dataSources.posts.LocalPostsDataSource
import com.shamlou.data_local.db.posts.daos.PostsDao
import com.shamlou.data_local.db.posts.PostsDataBase
import com.shamlou.data_remote.services.posts.PostsService
import com.shamlou.data_remote.dataSources.RemotePostsDataSource
import com.shamlou.domain.model.posts.ResponsePostsDomain
import com.shamlou.domain.repo.PostsRepository
import com.shamlou.domain.usecases.posts.GetPostsUseCase
import com.shamlou.featureone.di.FeatureOneScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class FeatureOneModule {

    @Binds
    @RemoteReadable
    @FeatureOneScope
    abstract fun bindPostsRemoteReadble(postsRemoteReadble : RemotePostsDataSource) : Readable<Unit, ResponsePostsData>

    @Binds
    @LocalReadable
    @FeatureOneScope
    abstract fun bindPostsLocalReadble(postsLocalReadble : LocalPostsDataSource) : Readable<Unit, ResponsePostsData>

    @Binds
    @FeatureOneScope
    abstract fun bindPostsLocalWritable(postsLocalWritable : LocalPostsDataSource) : Writable<ResponsePostsData, Unit>

    @Binds
    @FeatureOneScope
    abstract fun bindPostsRepository(repo : PostsRepositoryImpl) : PostsRepository

    @Binds
    @FeatureOneScope
    abstract fun bindPostsUseCase(useCase : GetPostsUseCase): FlowUseCase<Unit, ResponsePostsDomain>

    @Binds
    @FeatureOneScope
    abstract fun bindViewModelFactory(mapper: InjectingSavedStateViewModelFactory): ViewModelFactory

    companion object {


        @Provides
        @FeatureOneScope
        fun providePostsService(retrofit : Retrofit): PostsService {
            return retrofit.create(PostsService::class.java)
        }

        @Provides
        @FeatureOneScope
        fun providePostsDao(
            dataBase: PostsDataBase
        ): PostsDao {
            return dataBase.getPostsDao()
        }
    }
}