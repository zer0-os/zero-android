package com.zero.android.feature.messages.ui.messages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.zero.android.common.R
import com.zero.android.ui.components.AppBar
import com.zero.android.ui.components.Background
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

@Composable
fun MessagesRoute(viewModel: MessagesViewModel = hiltViewModel()) {
    MessagesScreen()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen() {
    val navController = rememberNavController()
    val messagesUiState = MessagesUiState(
        channelMembers = 0,
        channelName = "",
        initialMessages = emptyList()
    )

    val topBar: @Composable () -> Unit = {
        AppBar(
            scrollBehavior = null,
            navIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "cd_back",
                        tint = AppTheme.colors.glow
                    )
                }
            },
            title = { ConversationAppBarTitle() },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "cd_search_message"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "cd_more_options"
                    )
                }
            }
        )
    }
    Scaffold(
        /*modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding()
            .imePadding(),*/
        topBar = { topBar() }) {
        Background {
            MessagesContent(uiState = messagesUiState)
        }
    }
}

@Preview
@Composable
fun MessagesScreenPreview() = Preview {
    MessagesScreen()
}

@Composable
fun ConversationAppBarTitle() {
    val imageUrl = "https://cdn-icons-png.flaticon.com/512/2991/2991148.png"
    val title = "Game-dev"
    val showDiscord = true
    val showVector = true

    Row {
        IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = {}) {
            Icon(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "cd_channel_image"
            )
        }
        Text(
            title.lowercase(),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.padding(6.dp))
        if (showVector) {
            Image(
                painter = painterResource(R.drawable.ic_vector),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.padding(6.dp))
        }
        if (showDiscord) {
            Image(
                painter = painterResource(R.drawable.ic_discord),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Fit
            )
        }
    }
}
