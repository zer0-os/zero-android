package com.zero.android.network.service

import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import com.zero.android.network.chat.ChatListener
import com.zero.android.network.model.ApiMessage
import kotlinx.coroutines.flow.Flow

interface ChatService {

	suspend fun listen(channelId: String, listener: ChatListener)

	suspend fun getMessages(
		channel: Channel,
		timestamp: Long = Long.MAX_VALUE
	): Flow<List<ApiMessage>>

	suspend fun getMessages(channel: Channel, id: String): Flow<List<ApiMessage>>

	suspend fun send(channel: Channel, message: DraftMessage): Flow<ApiMessage>

	suspend fun reply(channel: Channel, id: String, message: DraftMessage): Flow<ApiMessage>

	suspend fun deleteMessage(channel: Channel, message: Message)
}
