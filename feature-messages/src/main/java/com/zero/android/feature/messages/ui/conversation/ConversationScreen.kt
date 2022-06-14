package com.zero.android.feature.messages.ui.conversation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.zero.android.common.R
import com.zero.android.feature.messages.helper.ConversationUiState
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.components.Background
import com.zero.android.ui.extensions.Preview

@Composable
fun ConversationRoute(viewModel: ConversationViewModel = hiltViewModel()) {
    ConversationScreen()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen() {
    val navController = rememberNavController()
    val conversationUiState = ConversationUiState(
        channelMembers = 0,
        channelName = "",
        initialMessages = emptyList()
    )
    val topBar: @Composable () -> Unit = {
        ConversationAppBar(
            scrollBehavior = null,
            onNavIconPressed = { navController.navigateUp() },
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
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        topBar = { topBar() }) {
        Background {
            ConversationContent(uiState = conversationUiState)
        }
    }
}

@Preview
@Composable
fun ConversationScreenPreview() = Preview {
    ConversationScreen()
}
