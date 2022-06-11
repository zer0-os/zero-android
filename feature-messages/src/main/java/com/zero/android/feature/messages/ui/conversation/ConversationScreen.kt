package com.zero.android.feature.messages.ui.conversation

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.zero.android.feature.messages.ui.conversation.channelappbar.ChannelConversationAppBar
import com.zero.android.models.fake.channel.ChannelRowMessage
import com.zero.android.ui.components.Background

@Composable
fun ConversationRoute(viewModel: ConversationViewModel = hiltViewModel()) {
    ConversationScreen()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen() {
    val isFromChannelRoute = true
    val navController = rememberNavController()
    val fakeChannel = ChannelRowMessage(
        0, "", "", "", "", 0, false, false
    )

    val topBar: @Composable () -> Unit = {
        ChannelConversationAppBar(
            navController = navController,
            channel = fakeChannel,
            onSearchClick = {

            },
            onMoreClick = {

            }
        )
    }
    val bottomBar: @Composable () -> Unit = { ConversationBottomBar() }
    Scaffold(
        topBar = { topBar() },
        bottomBar = { bottomBar() },
    ) {
        Background { ConversationContent() }
    }
}
