package com.zero.android.feature.messages.ui.conversation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.zero.android.common.R
import com.zero.android.ui.components.Background

@Composable
fun ConversationRoute(viewModel: ConversationViewModel = hiltViewModel()) {
    ConversationScreen()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen() {
    val navController = rememberNavController()

    val topBar: @Composable () -> Unit = {
        ConversationAppBar(
            scrollBehavior = null,
            onNavIconPressed = { navController.navigateUp() },
            title = { ConversationAppBarTitle() },
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "cd_search_message"
                    )
                }
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "cd_more_options"
                    )
                }
            }
        )
    }
    val bottomBar: @Composable () -> Unit = { ConversationBottomBar() }
    Scaffold(
        topBar = { topBar() },
        bottomBar = { bottomBar() },
    ) {
        Background {  }
    }
}
