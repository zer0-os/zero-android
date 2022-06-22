package com.zero.android.feature.messages.ui.messages

import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import java.util.*

data class ChatScreenUiState(
	val channelUiState: ChatChannelUiState,
	val messagesUiState: MessagesUiState
) {
	fun newMessage(msg: String, currentUserId: String) =
		DraftMessage(
			channelUrl = null,
			author = Member(currentUserId),
			type = MessageType.TEXT,
			mentionType = MessageMentionType.USER,
			message = msg,
			createdAt = Calendar.getInstance().timeInMillis,
			updatedAt = Calendar.getInstance().timeInMillis,
			status = MessageStatus.SUCCEEDED
		)
}

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
