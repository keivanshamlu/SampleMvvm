package com.shamlou.data_local.db.posts.daos

import androidx.room.Dao
import androidx.room.Query
import com.shamlou.data_local.model.posts.ResponsePostLocal
import com.shamlou.data_local.utility.BaseDao

@Dao
interface PostsDao : BaseDao<ResponsePostLocal> {

    @Query("DELETE FROM posts")
    fun deletePosts()

    @Query("SELECT * FROM posts")
    fun getAllPosts(): List<ResponsePostLocal>
}
