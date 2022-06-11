package com.zero.android.feature.messages.ui.conversation.channelappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.zero.android.common.R
import com.zero.android.models.fake.channel.ChannelRowMessage
import com.zero.android.ui.extensions.Preview

@Composable
fun ChannelConversationAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    channel: ChannelRowMessage,
    onSearchClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    MediumTopAppBar(
        modifier = modifier,
        navigationIcon = { ChannelConversationAppBarNavigationIcon(navController, channel) },
        title = { ChannelConversationAppBarTitle(channel) },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "cd_search_message"
                )
            }
            IconButton(onClick = onMoreClick) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "cd_more_options"
                )
            }
        }
    )
}

@Composable
fun ChannelConversationAppBarNavigationIcon(
    navController: NavController,
    channel: ChannelRowMessage
) {
    Row {
        IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "cd_back"
            )
        }
        IconButton(onClick = {

        }) {
            Icon(
                painter = rememberAsyncImagePainter(channel.image),
                contentDescription = "cd_channel_image"
            )
        }
    }
}

@Composable
fun ChannelConversationAppBarTitle(channel: ChannelRowMessage) {
    Row {
        Text(channel.message.lowercase())
        Spacer(modifier = Modifier.padding(4.dp))
        if (channel.isVector) {
            Image(
                painter = painterResource(R.drawable.ic_vector),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.padding(4.dp))
        }
        if (channel.isDiscord) {
            Image(
                painter = painterResource(R.drawable.ic_discord),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Preview
@Composable
fun ChannelConversationAppBarPreview() = Preview {
    val fakeChannel = ChannelRowMessage(
        0, "", "", "", "", 0, false, false
    )
    val navController = rememberNavController()
    ChannelConversationAppBar(
        navController = navController,
        channel = fakeChannel,
        onSearchClick = {

        },
        onMoreClick = {

        }
    )
}
