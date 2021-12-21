package com.shamlou.data_local.db.posts

import androidx.room.*
import com.shamlou.data_local.db.posts.daos.PostsDao
import com.shamlou.data_local.model.posts.ResponsePostLocal

@Database(
    entities = [ResponsePostLocal::class],
    version = 1,
    exportSchema = false
)
abstract class PostsDataBase : RoomDatabase() {


    abstract fun getPostsDao() : PostsDao
}
