package com.zero.android.feature.messages.ui.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.zero.android.common.R
import com.zero.android.ui.theme.AppTheme

@Composable
fun ConversationAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = {},
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Box(modifier = Modifier) {
        SmallTopAppBar(
            modifier = modifier,
            actions = actions,
            title = title,
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = onNavIconPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "cd_back",
                        tint = AppTheme.colors.glow
                    )
                }
            }
        )
    }
}

@Composable
fun ConversationAppBarTitle() {
    val imageUrl = "https://cdn-icons-png.flaticon.com/512/2991/2991148.png"
    val title = "Game-dev"
    val showDiscord = true
    val showVector = true

    Row {
        IconButton(modifier = Modifier.align(CenterVertically), onClick = {}) {
            Icon(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "cd_channel_image"
            )
        }
        Text(
            title.lowercase(),
            modifier = Modifier.align(CenterVertically)
        )
        Spacer(modifier = Modifier.padding(6.dp))
        if (showVector) {
            Image(
                painter = painterResource(R.drawable.ic_vector),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentSize()
                    .align(CenterVertically),
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
                    .align(CenterVertically),
                contentScale = ContentScale.Fit
            )
        }
    }
}
