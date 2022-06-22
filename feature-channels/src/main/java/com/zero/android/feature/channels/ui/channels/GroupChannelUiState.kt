package com.zero.android.feature.channels.ui.channels

import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel
import com.zero.android.models.fake.ChannelTab

data class GroupChannelUiState(
    val categoriesUiState: ChannelCategoriesUiState,
    val categoryChannelsUiState: CategoryChannelsUiState
){
    private val groupedChannels = if (categoryChannelsUiState is CategoryChannelsUiState.Success) {
        categoryChannelsUiState.channels.groupBy { if ((it as GroupChannel).category.isNullOrEmpty()) "Private" else it.category }
    } else null

    fun getChannels(category: String): List<Channel> {
        return groupedChannels?.get(category) ?: emptyList()
    }
}

sealed interface ChannelCategoriesUiState {
    data class Success(val categories: List<ChannelTab>) : ChannelCategoriesUiState
    object Error : ChannelCategoriesUiState
    object Loading : ChannelCategoriesUiState
}

sealed interface CategoryChannelsUiState {
    data class Success(val channels: List<Channel>) : CategoryChannelsUiState
    object Error : CategoryChannelsUiState
    object Loading : CategoryChannelsUiState
}
