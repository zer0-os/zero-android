package com.zero.android.feature.messages.ui.messages

import com.zero.android.models.Channel
import com.zero.android.models.Message

data class ChatScreenUiState(
	val channelUiState: ChatChannelUiState,
	val messagesUiState: MessagesUiState
)

sealed interface ChatChannelUiState {
	data class Success(val channel: Channel) : ChatChannelUiState
	object Error : ChatChannelUiState
	object Loading : ChatChannelUiState
}

sealed interface MessagesUiState {
	data class Success(val messages: List<Message>) : MessagesUiState
	object Error : MessagesUiState
	object Loading : MessagesUiState
}
