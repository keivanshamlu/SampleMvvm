package com.shamlou.core.modules

import android.content.Context
import androidx.room.Room
import com.shamlou.core.anots.ApplicationContext
import com.shamlou.data_local.db.posts.PostsDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        PostsDataBase::class.java,
        "BuildConfig.DATABASE_NAME"
    ).build()

    @Provides
    @Singleton
    fun provideAssignmentsDao(appDatabase: PostsDataBase) = appDatabase.getPostsDao()

}
