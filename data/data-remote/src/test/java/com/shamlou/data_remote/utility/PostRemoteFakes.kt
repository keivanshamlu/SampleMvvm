package com.shamlou.data_remote.utility

import com.github.javafaker.Faker
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_remote.model.posts.ResponsePostRemote
import java.io.IOException

object PostRemoteFakes {
    private val faker = Faker()


    val listSampleResponsePostRemote = faker.getListSampleResponsePostRemote()
    val sampleResponsePostData = faker.getSampleResponsePostData()
    val compatibleListRemoteData = getListResponsePostCompatible()
    val exception = IOException(faker.lorem().sentence())
}

object ValidGetPostsResponse{
    val validGetPostsResponse = """
        [
          {
            "userId": 1,
            "id": 1,
            "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            "body": "quia et suscipit nsuscipit recusandae consequuntur expedita et cum nreprehenderit molestiae ut ut quas totam nnostrum rerum est autem sunt rem eveniet architecto"
          },
          {
            "userId": 1,
            "id": 2,
            "title": "qui est esse",
            "body": "est rerum tempore vitae nsequi sint nihil reprehenderit dolor beatae ea dolores neque nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis nqui aperiam non debitis possimus qui neque nisi nulla"
          }
        ]
    """.trimIndent()
    val validResponsePostsRemote = listOf(
        ResponsePostRemote(1,1,"sunt aut facere repellat provident occaecati excepturi optio reprehenderit" , "quia et suscipit nsuscipit recusandae consequuntur expedita et cum nreprehenderit molestiae ut ut quas totam nnostrum rerum est autem sunt rem eveniet architecto"),
        ResponsePostRemote(2,1,"qui est esse" , "est rerum tempore vitae nsequi sint nihil reprehenderit dolor beatae ea dolores neque nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis nqui aperiam non debitis possimus qui neque nisi nulla")
    )
    val validResponsePostsData = listOf(
        ResponsePostData(1,1,"sunt aut facere repellat provident occaecati excepturi optio reprehenderit" , "quia et suscipit nsuscipit recusandae consequuntur expedita et cum nreprehenderit molestiae ut ut quas totam nnostrum rerum est autem sunt rem eveniet architecto"),
        ResponsePostData(2,1,"qui est esse" , "est rerum tempore vitae nsequi sint nihil reprehenderit dolor beatae ea dolores neque nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis nqui aperiam non debitis possimus qui neque nisi nulla")
    )

}

object CompatibleResponsePost {
    val compatibalePostRemote = ResponsePostRemote(1111, 2222, "title", "body")
    val compatibalePostData = ResponsePostData(1111, 2222, "title", "body")

    val compatibalePostRemoteNull =ResponsePostRemote(null, null, null, null)
    val compatibalePostDataEmpty =ResponsePostData(-1, -1, "", "")
}

fun Faker.getSampleResponsePostData() = ResponsePostData(
    number().randomDigit(),
    number().randomDigit(),
    address().streetName(),
    address().fullAddress()
)

fun Faker.getSampleResponsePostRemote() = ResponsePostRemote(
    number().randomDigit(),
    number().randomDigit(),
    address().streetName(),
    address().fullAddress()
)

fun Faker.getListSampleResponsePostRemote(): List<ResponsePostRemote> {

    mutableListOf<ResponsePostRemote>().apply {
        for (i in 0..(0..10).random()) {
            add(getSampleResponsePostRemote())
        }
    }.also { return it }
}

fun getListResponsePostCompatible(): Pair<List<ResponsePostRemote> , List<ResponsePostData>> {

    val remote = mutableListOf<ResponsePostRemote>()
    val data = mutableListOf<ResponsePostData>()
    for (i in 0..(0..10).random()) {
        remote.add(CompatibleResponsePost.compatibalePostRemote)
        data.add(CompatibleResponsePost.compatibalePostData)
    }
    return Pair(remote , data)
}