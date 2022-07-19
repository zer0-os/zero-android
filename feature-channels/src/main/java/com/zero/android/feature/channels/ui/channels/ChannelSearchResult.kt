package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel

@Composable
fun ChannelSearchResult(
    channels: List<Channel>
) {
    val categoryWiseCount =
        (channels as List<GroupChannel>).groupBy { if (it.category.isNullOrEmpty()) "Other" else it.category }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            for (index in channels.indices) {
                val previousChannel = channels.getOrNull(index - 1)
                val currentChannel = channels[index]
                val showHeader = !currentChannel.category.equals(previousChannel?.category, true)
                item {
                    ChannelSearchItem(currentChannel, showHeader)
                }
            }
        }
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
fun ChannelSearchItem(channel: GroupChannel, showHeader: Boolean = true) {

}
