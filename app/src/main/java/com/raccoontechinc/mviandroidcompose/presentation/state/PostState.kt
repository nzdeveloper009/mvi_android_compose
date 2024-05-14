package com.raccoontechinc.mviandroidcompose.presentation.state

import com.raccoontechinc.mviandroidcompose.data.remote.model.Post

data class PostState(
    val progressbar: Boolean = false,
    val posts: List<Post> = emptyList()
)
