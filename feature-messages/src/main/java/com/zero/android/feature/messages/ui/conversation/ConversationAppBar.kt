package com.zero.android.feature.messages.ui.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onNavIconPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    ).value
    val foregroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )
    Box(modifier = Modifier.background(backgroundColor)) {
        MediumTopAppBar(
            modifier = modifier,
            actions = actions,
            title = title,
            scrollBehavior = scrollBehavior,
            colors = foregroundColors,
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
    val imageUrl = ""
    val title = "Game-dev"
    val showDiscord = false
    val showVector = true

    Row {
        IconButton(onClick = {

        }) {
            Icon(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "cd_channel_image"
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Text(title.lowercase())
        Spacer(modifier = Modifier.padding(4.dp))
        if (showVector) {
            Image(
                painter = painterResource(R.drawable.ic_vector),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.padding(4.dp))
        }
        if (showDiscord) {
            Image(
                painter = painterResource(R.drawable.ic_discord),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
