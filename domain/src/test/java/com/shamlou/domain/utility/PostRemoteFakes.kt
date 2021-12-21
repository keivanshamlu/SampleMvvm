package com.shamlou.domain.utility

import com.github.javafaker.Faker
import com.shamlou.domain.model.posts.ResponsePostDomain
import java.io.IOException

object PostRemoteFakes {
    private val faker = Faker()


    val listSampleResponsePostDomain = faker.getListSampleResponsePostDomain()
    val sampleResponsePostDomain = faker.getSampleResponsePostDomain()
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
    val validResponsePostsDomain = listOf(
        ResponsePostDomain(1,1,"sunt aut facere repellat provident occaecati excepturi optio reprehenderit" , "quia et suscipit nsuscipit recusandae consequuntur expedita et cum nreprehenderit molestiae ut ut quas totam nnostrum rerum est autem sunt rem eveniet architecto"),
        ResponsePostDomain(2,1,"qui est esse" , "est rerum tempore vitae nsequi sint nihil reprehenderit dolor beatae ea dolores neque nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis nqui aperiam non debitis possimus qui neque nisi nulla")
    )

}


fun Faker.getSampleResponsePostDomain() = ResponsePostDomain(
    number().randomDigit(),
    number().randomDigit(),
    address().streetName(),
    address().fullAddress()
)

fun Faker.getListSampleResponsePostDomain(): List<ResponsePostDomain> {

    mutableListOf<ResponsePostDomain>().apply {
        for (i in 0..(0..10).random()) {
            add(getSampleResponsePostDomain())
        }
    }.also { return it }
}
