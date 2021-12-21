package com.shamlou.data_local.dataSources.posts


import com.shamlou.bases.mapper.Mapper
import com.shamlou.bases.readWrite.Readable
import com.shamlou.bases.readWrite.Writable
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data.model.posts.ResponsePostsData
import com.shamlou.data_local.model.posts.ResponsePostLocal
import com.shamlou.data_local.db.posts.daos.PostsDao
import javax.inject.Inject

class LocalPostsDataSource @Inject constructor(
    private val postsDao: PostsDao,
    private val mapperDataToLocal: Mapper<ResponsePostData, ResponsePostLocal>,
    private val mapperLocalToData: Mapper<ResponsePostLocal, ResponsePostData>,
) : Readable<Unit, ResponsePostsData>,
    Writable<ResponsePostsData, Unit> {

    override suspend fun read(input: Unit): ResponsePostsData {
        return postsDao.getAllPosts().map { mapperLocalToData.map(it) }.let { ResponsePostsData(it) }
    }
    override suspend fun write(input: ResponsePostsData) {
        postsDao.insertAll(input.posts.map { mapperDataToLocal.map(it) })
    }
}