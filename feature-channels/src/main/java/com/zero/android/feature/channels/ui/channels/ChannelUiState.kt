package com.zero.android.feature.channels.ui.channels

import com.zero.android.common.ui.Result
import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel
import com.zero.android.models.fake.ChannelTab

class ChannelUiState(
    channels: Result<List<Channel>>
) {
    private val filteredResult = if (channels is Result.Success) {
        channels.data.filter { !(it as GroupChannel).category.isNullOrEmpty() }
    } else emptyList()

    val channelGroupMessages = filteredResult.groupBy { (it as GroupChannel).category!! }
    val channelCategories = filteredResult.map { channel ->
        val unreadGroupMessagesCount =
            channelGroupMessages[(channel as GroupChannel).category!!]?.count { it.unreadMessageCount > 0 } ?: 0
        ChannelTab(
            (channel as GroupChannel).category!!.hashCode().toLong(),
            (channel as GroupChannel).category!!,
            unreadGroupMessagesCount
        )
    }
}

fun ChannelUiState.groupMessages(category: String) = channelGroupMessages[category]
