package com.shamlou.data_local.utility

import com.github.javafaker.Faker
import com.shamlou.data.model.posts.ResponsePostData
import com.shamlou.data_local.model.posts.ResponsePostLocal
import java.io.IOException

object PostRemoteFakes {
    private val faker = Faker()


    val listSampleResponsePostLocal = faker.getListSampleResponsePostLocal()
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
    val validResponsePostsLocal = listOf(
        ResponsePostLocal(1,1,"sunt aut facere repellat provident occaecati excepturi optio reprehenderit" , "quia et suscipit nsuscipit recusandae consequuntur expedita et cum nreprehenderit molestiae ut ut quas totam nnostrum rerum est autem sunt rem eveniet architecto"),
        ResponsePostLocal(2,1,"qui est esse" , "est rerum tempore vitae nsequi sint nihil reprehenderit dolor beatae ea dolores neque nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis nqui aperiam non debitis possimus qui neque nisi nulla")
    )
    val validResponsePostsData = listOf(
        ResponsePostData(1,1,"sunt aut facere repellat provident occaecati excepturi optio reprehenderit" , "quia et suscipit nsuscipit recusandae consequuntur expedita et cum nreprehenderit molestiae ut ut quas totam nnostrum rerum est autem sunt rem eveniet architecto"),
        ResponsePostData(2,1,"qui est esse" , "est rerum tempore vitae nsequi sint nihil reprehenderit dolor beatae ea dolores neque nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis nqui aperiam non debitis possimus qui neque nisi nulla")
    )

}

object CompatibleResponsePost {
    val compatibalePostLocal = ResponsePostLocal(1111, 2222, "title", "body")
    val compatibalePostData = ResponsePostData(1111, 2222, "title", "body")
}

fun Faker.getSampleResponsePostData() = ResponsePostData(
    number().randomDigit(),
    number().randomDigit(),
    address().streetName(),
    address().fullAddress()
)

fun Faker.getSampleResponsePostLocal() = ResponsePostLocal(
    number().randomDigit(),
    number().randomDigit(),
    address().streetName(),
    address().fullAddress()
)

fun Faker.getListSampleResponsePostLocal(): List<ResponsePostLocal> {

    mutableListOf<ResponsePostLocal>().apply {
        for (i in 0..(0..10).random()) {
            add(getSampleResponsePostLocal())
        }
    }.also { return it }
}

fun getListResponsePostCompatible(): Pair<List<ResponsePostLocal> , List<ResponsePostData>> {

    val local = mutableListOf<ResponsePostLocal>()
    val data = mutableListOf<ResponsePostData>()
    for (i in 0..(0..10).random()) {
        local.add(CompatibleResponsePost.compatibalePostLocal)
        data.add(CompatibleResponsePost.compatibalePostData)
    }
    return Pair(local , data)
}