package com.raccoontechinc.mviandroidcompose.data.remote.service

import com.raccoontechinc.mviandroidcompose.data.remote.api.PostApi
import com.raccoontechinc.mviandroidcompose.data.remote.model.Post
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class PostApiImpl(
    private val httpClient: HttpClient
) : PostApi {

    override suspend fun getPosts(): List<Post> =
        httpClient.get {
            url("https://jsonplaceholder.typicode.com/posts")
        }.body()
}