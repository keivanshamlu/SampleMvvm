package com.shamlou.data.repoImpl

import com.shamlou.bases.mapper.Mapper
import com.shamlou.bases.readWrite.Readable
import com.shamlou.bases.readWrite.Writable
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data.model.posts.ResponsePostsData
import com.shamlou.data.read_write.posts.LocalReadable
import com.shamlou.data.read_write.posts.RemoteReadable
import com.shamlou.data.utility.networkWithCache
import com.shamlou.domain.repo.PostsRepository
import com.shamlou.domain.model.posts.ResponsePostDomain
import com.shamlou.domain.model.posts.ResponsePostsDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PostsRepositoryImpl @Inject constructor(
    @LocalReadable private val postsLocalReadable : Readable<Unit, ResponsePostsData>,
    private val postsLocalWritable : Writable<ResponsePostsData, Unit>,
    @RemoteReadable private val postsRemoteReadable : Readable<Unit, ResponsePostsData>,
    private val mapperPostDataToDomain : Mapper<ResponsePostData, ResponsePostDomain>
) : PostsRepository {

    override fun getAllPosts(param: Unit): Flow<ResponsePostsDomain> {

        return flow {
            networkWithCache(
                createCall = { postsRemoteReadable.read(Unit) },
                loadFromLocal = { postsLocalReadable.read(Unit) },
                shouldFetch = { postsLocalReadable.read(Unit).posts.isNullOrEmpty() },
                saveCallResult = { postsLocalWritable.write(it) }
            ).posts.map { mapperPostDataToDomain.map(it) }.also {

                emit(ResponsePostsDomain(it))
            }
        }
    }

    override fun removeAll(): Flow<Unit> {
        TODO("Not yet implemented")
    }


}