package com.raccoontechinc.mviandroidcompose.presentation.view.post

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raccoontechinc.mviandroidcompose.presentation.common.UIComponent
import com.raccoontechinc.mviandroidcompose.presentation.viewmodel.PostViewModel
import com.raccoontechinc.mviandroidcompose.ui.theme.MVIAndroidComposeTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<PostViewModel>()
        enableEdgeToEdge()
        setContent {
            MVIAndroidComposeTheme {
                val state by viewModel.collectAsState()
                val context = LocalContext.current
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(state.posts) { post ->
                            Column {
                                val title = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = Color.Black,fontWeight = FontWeight.Black)) {
                                        append("Title: ")
                                    }
                                    withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Light)) {
                                        append(post.title)
                                    }
                                }
                                val body = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Black)) {
                                        append("Body: ")
                                    }
                                    withStyle(style = SpanStyle(color = Color.DarkGray, fontWeight = FontWeight.Light)) {
                                        append(post.body)
                                    }
                                }
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 18.sp)
                                )
                                Text(
                                    text = body,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 10.sp)
                                )
                            }
                        }
                    }

                    if (state.progressbar) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    viewModel.collectSideEffect { uiComponent ->
                        when (uiComponent) {
                            is UIComponent.Toast -> {
                                Toast.makeText(context, uiComponent.text, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
