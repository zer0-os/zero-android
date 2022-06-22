package com.zero.android.feature.channels.ui.channels

import com.zero.android.common.ui.Result
import com.zero.android.models.Channel
import com.zero.android.models.ChannelCategory
import com.zero.android.models.GroupChannel
import com.zero.android.models.fake.ChannelTab

class GroupChannelUiState(
	categories: Result<List<ChannelCategory>>,
	channels: Result<List<Channel>>
) {
	private val filteredChannels =
		if (channels is Result.Success) {
			channels.data.filter { !(it as GroupChannel).category.isNullOrEmpty() }
		} else emptyList()
	private val filteredCategories =
		if (categories is Result.Success) {
			categories.data.filter { it.isNotEmpty() }
		} else emptyList()

	val channelGroupMessages = filteredChannels.groupBy { (it as GroupChannel).category!! }
	val channelCategories =
		filteredCategories.map { category ->
			val unreadGroupMessagesCount =
				channelGroupMessages[category]?.count { it.unreadMessageCount > 0 } ?: 0
			ChannelTab(category.hashCode().toLong(), category, unreadGroupMessagesCount)
		}
}

fun GroupChannelUiState.groupMessages(category: String) = channelGroupMessages[category]
