package com.zero.android.feature.channels.ui.directchannels

import com.zero.android.models.Channel

data class DirectChannelScreenUiState(val directChannelsUiState: DirectChannelUiState)

sealed interface DirectChannelUiState {
	data class Success(val channels: List<Channel>, val isSearchResult: Boolean = false) :
		DirectChannelUiState
	object Error : DirectChannelUiState
	object Loading : DirectChannelUiState
}
