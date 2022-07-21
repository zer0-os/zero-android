package com.zero.android.feature.channels.ui.channels

import com.zero.android.feature.channels.model.ChannelTab
import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel

data class GroupChannelUiState(
	val categoriesUiState: ChannelCategoriesUiState,
	val categoryChannelsUiState: CategoryChannelsUiState
) {
	private val allChannels =
		if (categoryChannelsUiState is CategoryChannelsUiState.Success) {
			categoryChannelsUiState.channels
		} else emptyList()
	private val groupedChannels = allChannels.groupBy { (it as GroupChannel).category }

	fun getChannels(category: String): List<Channel> {
		return if (category.equals("All", true)) allChannels
		else groupedChannels[category] ?: emptyList()
	}
}

sealed interface ChannelCategoriesUiState {
	data class Success(val categories: List<ChannelTab>) : ChannelCategoriesUiState
	object Error : ChannelCategoriesUiState
	object Loading : ChannelCategoriesUiState
}

sealed interface CategoryChannelsUiState {
	data class Success(val channels: List<Channel>, val isSearchResult: Boolean = false) :
		CategoryChannelsUiState
	object Error : CategoryChannelsUiState
	object Loading : CategoryChannelsUiState
}
