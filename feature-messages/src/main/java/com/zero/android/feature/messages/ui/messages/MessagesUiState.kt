package com.zero.android.feature.messages.ui.messages

import androidx.compose.runtime.mutableStateListOf
import com.zero.android.common.ui.Result
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import java.util.*

class MessagesUiState(
	loggedInUserId: String,
	channelResult: Result<Channel>,
	messages: Result<List<Message>>
) {
	val currentUserId = loggedInUserId

	val channel =
		if (channelResult is Result.Success) {
			channelResult.data
		} else null

	private val _messages: MutableList<Message> =
		if (messages is Result.Success) {
			mutableStateListOf(*messages.data.toTypedArray())
		} else mutableListOf()
	val messages: List<Message> = _messages

	fun addMessage(msg: String) =
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
