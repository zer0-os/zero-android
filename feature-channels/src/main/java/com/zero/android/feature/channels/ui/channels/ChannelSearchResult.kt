package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel
import com.zero.android.models.getTitle
import com.zero.android.ui.components.NameInitialsView
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun ChannelSearchResult(
    channels: List<Channel>
) {
    val categorisedChannels =
        (channels as List<GroupChannel>).groupBy { if (it.category.isNullOrEmpty()) "Other" else it.category }
    Column(modifier = Modifier.fillMaxSize()) {
        categorisedChannels.forEach { entry ->
            entry.key?.let {
                ChannelSearchItem(it, entry.value)
            }
        }
        /*LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            for (index in channels.indices) {
                val previousChannel = channels.getOrNull(index - 1) as? GroupChannel
                val currentChannel = channels[index] as GroupChannel
                val showHeader = !currentChannel.category.equals(previousChannel?.category, true)
                item {
                    ChannelSearchItem(currentChannel, showHeader)
                }
            }
        }*/
        Text(
            text = "${channels.size} results found",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .background(MaterialTheme.colorScheme.primary),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ChannelSearchItem(header: String, channels: List<GroupChannel>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            Text(header, color = AppTheme.colors.colorTextPrimary, style = MaterialTheme.typography.bodyMedium)
            Text("${channels.size} found", color = AppTheme.colors.colorTextSecondary, style = MaterialTheme.typography.labelLarge)
        }
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(channels, itemContent = { channel ->
                Row() {
                    NameInitialsView(
                        modifier = Modifier.size(24.dp),
                        userName = channel.getTitle()
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = channel.getTitle(),
                        color = AppTheme.colors.colorTextPrimary,
                        style = Typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (channel.hasTelegramChannel) {
                        Spacer(modifier = Modifier.padding(4.dp))
                        Image(
                            painter = painterResource(R.drawable.ic_vector),
                            contentDescription = "",
                            modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
                            contentScale = ContentScale.Fit
                        )
                    }
                    if (channel.hasDiscordChannel) {
                        Spacer(modifier = Modifier.padding(4.dp))
                        Image(
                            painter = painterResource(R.drawable.ic_discord),
                            contentDescription = "",
                            modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            })
        }
    }
}
