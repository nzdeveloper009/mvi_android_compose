package com.raccoontechinc.mviandroidcompose.domain.usecase

import com.raccoontechinc.mviandroidcompose.presentation.common.DataState
import com.raccoontechinc.mviandroidcompose.presentation.common.UIComponent
import com.raccoontechinc.mviandroidcompose.data.remote.api.PostApi
import com.raccoontechinc.mviandroidcompose.data.remote.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// use case

class GetPosts(
    private val postApi: PostApi
) {

    fun execute(): Flow<DataState<List<Post>>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                val posts = postApi.getPosts()
                emit(DataState.Success(posts))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(UIComponent.Toast(e.message ?: "Unknown error")))
            } finally {
                emit(DataState.Loading(false))
            }
        }
    }
}