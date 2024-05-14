package com.raccoontechinc.mviandroidcompose.data.remote.api

import com.raccoontechinc.mviandroidcompose.data.remote.service.PostApiImpl
import com.raccoontechinc.mviandroidcompose.data.remote.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface PostApi {

    suspend fun getPosts(): List<Post>

    companion object {
        val httpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        this.ignoreUnknownKeys = true
                    }
                )
            }
        }

        fun providePostApi(): PostApi = PostApiImpl(httpClient)
    }
}