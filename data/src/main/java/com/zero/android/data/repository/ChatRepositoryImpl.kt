package com.zero.android.data.repository

import com.zero.android.data.conversion.toModel
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageType
import com.zero.android.network.chat.ChatListener
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiDeleteMessage
import com.zero.android.network.model.ApiMessage
import com.zero.android.network.service.ChatMediaService
import com.zero.android.network.service.ChatService
import com.zero.android.network.service.MessageService
import com.zero.android.network.util.ChatMediaUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class ChatRepositoryImpl
@Inject
constructor(
	private val chatService: ChatService,
	private val chatMediaService: ChatMediaService,
	private val chatMediaUtil: ChatMediaUtil,
	private val messageService: MessageService
) : ChatRepository {

	override val channelChatMessages = MutableStateFlow<List<Message>>(emptyList())

	override suspend fun getMessages(channel: Channel, timestamp: Long): Flow<List<Message>> {
		val messagesResult =
			chatService.getMessages(channel, timestamp).map { messages ->
				messages.map { it.toModel() }
			}
		messagesResult.collect(channelChatMessages)
		return messagesResult
	}

	override suspend fun addChatListener(channel: Channel) {
		chatService.listen(
			channel.id,
			object : ChatListener {
				override fun onMessageReceived(var1: ApiChannel, var2: ApiMessage) {
					runBlocking(Dispatchers.IO) { appendNewChatMessage(var2.toModel()) }
				}
			}
		)
	}

	override suspend fun getMessages(channel: Channel, id: String): Flow<List<Message>> {
		return chatService.getMessages(channel, id).map { messages -> messages.map { it.toModel() } }
	}

	override suspend fun send(channel: Channel, message: DraftMessage): Flow<Message> {
		return if (message.type == MessageType.TEXT) {
			val newMessage = chatService.send(channel, message).map { it.toModel() }
			newMessage.collectLatest { appendNewChatMessage(it) }
			newMessage
		} else {
			sendFileMessage(channel, message)
		}
	}

	private suspend fun sendFileMessage(channel: Channel, message: DraftMessage): Flow<Message> {
		val uploadInfo = chatMediaService.getUploadInfo()
		val fileMessage =
			if (uploadInfo.apiUrl.isNotEmpty() && uploadInfo.query != null) {
				val fileUpload =
					chatMediaService.uploadMediaFile(
						chatMediaUtil.getUploadUrl(uploadInfo),
						chatMediaUtil.getUploadBody(message.file!!)
					)
				message.copy(
					fileUrl = fileUpload.secureUrl,
					fileName = fileUpload.originalFilename,
					data = JSONObject(fileUpload.getDataMap(message.type)).toString()
				)
			} else {
				Timber.e("Upload Info is required for file upload")
				message
			}
		val newMessage = chatService.send(channel, fileMessage).map { it.toModel() }
		newMessage.collectLatest { appendNewChatMessage(it) }
		return newMessage
	}

	override suspend fun reply(channel: Channel, id: String, message: DraftMessage): Flow<Message> {
		return send(channel, message.apply { parentMessageId = id })
	}

    override suspend fun updateMessage(id: String, channelId: String, text: String) {
        val response = messageService.updateMessage(id, channelId, text)
        if (response.isSuccessful) {
            channelChatMessages.update { messages ->
                val updatedMessage = messages.firstOrNull { it.id == id }?.copy(
                    message = text
                )
                if (updatedMessage != null) {
                    messages.toMutableList().apply { this[messages.indexOfFirst { it.id == id }] = updatedMessage }
                } else messages
            }
        }
    }

    override suspend fun deleteMessage(message: Message, channelId: String) {
        val response = messageService.deleteMessage(message.id, ApiDeleteMessage(channelId))
        if (response.isSuccessful) {
            channelChatMessages.update { messages ->
                messages.toMutableList().apply { this.remove(message) }
            }
        }
    }

	private suspend fun appendNewChatMessage(message: Message) {
		val messages = channelChatMessages.firstOrNull()?.toMutableList() ?: mutableListOf()
		messages.add(0, message)
		channelChatMessages.tryEmit(messages)
	}
}
