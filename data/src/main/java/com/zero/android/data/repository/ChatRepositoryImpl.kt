package com.zero.android.data.repository

import com.zero.android.data.conversion.toModel
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import com.zero.android.network.service.ChatService
import com.zero.android.network.service.MessageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl
@Inject
constructor(private val chatService: ChatService, private val messageService: MessageService) :
	ChatRepository {

	override suspend fun getMessages(channel: Channel, timestamp: Long): Flow<List<Message>> {
		return chatService.getMessages(channel, timestamp).map { messages ->
			messages.map { it.toModel() }
		}
	}

	override suspend fun getMessages(channel: Channel, id: String): Flow<List<Message>> {
		return chatService.getMessages(channel, id).map { messages -> messages.map { it.toModel() } }
	}

	override suspend fun send(channel: Channel, message: DraftMessage): Flow<Message> {
		return chatService.send(channel, message).map { it.toModel() }
	}

	override suspend fun reply(channel: Channel, id: String, message: DraftMessage): Flow<Message> {
		return chatService.reply(channel, id, message).map { it.toModel() }
	}

	override suspend fun updateMessage(id: String, channelId: String, text: String) {
		messageService.updateMessage(id, channelId, text)
	}

	override suspend fun deleteMessage(id: String, channelId: String) {
		return messageService.deleteMessage(id, channelId)
	}
}
