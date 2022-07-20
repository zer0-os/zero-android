package com.zero.android.feature.messages.ui.messages

import androidx.paging.PagingData
import com.zero.android.common.ui.Result
import com.zero.android.models.Channel
import com.zero.android.models.Message

data class ChatScreenUiState(
	val channelUiState: ChannelUIState,
	val messagesUiState: MessagesUIState
)

typealias ChannelUIState = Result<Channel>

typealias MessagesUIState = Result<PagingData<Message>>
