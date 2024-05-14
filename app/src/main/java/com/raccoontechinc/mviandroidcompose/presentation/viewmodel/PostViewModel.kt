package com.raccoontechinc.mviandroidcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raccoontechinc.mviandroidcompose.presentation.common.DataState
import com.raccoontechinc.mviandroidcompose.presentation.common.UIComponent
import com.raccoontechinc.mviandroidcompose.data.remote.api.PostApi
import com.raccoontechinc.mviandroidcompose.domain.usecase.GetPosts
import com.raccoontechinc.mviandroidcompose.presentation.state.PostState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class PostViewModel : ViewModel(), ContainerHost<PostState, UIComponent> {

    // use-case
    private val getPosts = GetPosts(PostApi.providePostApi())

    // state
    override val container: Container<PostState, UIComponent> = container(PostState())

    init {
        getPosts()
    }

    private fun getPosts() {
        intent {
            val post = getPosts.execute()
            post.onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        reduce {
                            state.copy(progressbar = dataState.isLoading)
                        }
                    }

                    is DataState.Success -> {
                        reduce {
                            state.copy(posts = dataState.data)
                        }
                    }

                    is DataState.Error -> {
                        when (dataState.uiComponent) {
                            is UIComponent.Toast -> {
                                postSideEffect(UIComponent.Toast(dataState.uiComponent.text))
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}