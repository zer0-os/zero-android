package com.zero.android.data.repository

import com.zero.android.data.conversion.toEntity
import com.zero.android.data.conversion.toModel
import com.zero.android.database.dao.MessageDao
import com.zero.android.database.model.MessageWithRefs
import com.zero.android.database.model.toModel
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageType
import com.zero.android.network.chat.ChatListener
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiMessage
import com.zero.android.network.service.ChatMediaService
import com.zero.android.network.service.ChatService
import com.zero.android.network.service.MessageService
import com.zero.android.network.util.NetworkMediaUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class ChatRepositoryImpl
@Inject
constructor(
	private val chatService: ChatService,
    private val messageService: MessageService,
    private val chatMediaService: ChatMediaService,
    private val networkMediaUtil: NetworkMediaUtil,
    private val messageDao: MessageDao
) : ChatRepository {

	override fun allChatMessages(channelId: String) = messageDao.getByChannel(channelId)
        .map { messageEntities -> messageEntities.map { it.toModel() }
    }

	override suspend fun addListener(id: String) {
		chatService.addListener(
			id,
			object : ChatListener {
				override fun onMessageReceived(var1: ApiChannel, var2: ApiMessage) {
					runBlocking(Dispatchers.IO) { messageDao.upsert(var2.toEntity()) }
				}
			}
		)
	}

	override suspend fun removeListener(id: String) = chatService.removeListener(id)

	override suspend fun getMessages(channel: Channel, timestamp: Long): Flow<List<Message>> {
		val messagesResult = chatService.getMessages(channel, timestamp)
        val messages = messagesResult.map { messages ->
            val entities = messages.map { it.toEntity() }
            messageDao.upsert(*entities.toTypedArray())
            messages.map { it.toModel() }
        }
		return messages
	}

	override suspend fun getMessages(channel: Channel, id: String): Flow<List<Message>> {
		return chatService.getMessages(channel, id).map { messages -> messages.map { it.toModel() } }
	}

	override suspend fun send(channel: Channel, message: DraftMessage): Flow<Message> {
		return if (message.type == MessageType.TEXT) {
			chatService.send(channel, message).map {
				messageDao.upsert(it.toEntity())
                it.toModel()
			}
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
						networkMediaUtil.getUploadUrl(uploadInfo),
						networkMediaUtil.getUploadBody(message.file!!)
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
		val newMessage = chatService.send(channel, fileMessage).map {
            messageDao.upsert(it.toEntity())
            it.toModel()
        }
		return newMessage
	}

	override suspend fun reply(channel: Channel, id: String, message: DraftMessage): Flow<Message> {
		return send(channel, message.apply { parentMessageId = id })
	}

    override suspend fun updateMessage(id: String, channelId: String, text: String) {
        val response = messageService.updateMessage(id, channelId, text)
        if (response.isSuccessful) {
            messageDao.get(id).collectLatest { existingMessage ->
                existingMessage?.let {
                    val updatedMessageEntity = it.message.copy(message = text)
                    messageDao.upsert(it.copy(message = updatedMessageEntity))
                }
            }
        }
    }

    override suspend fun deleteMessage(message: Message, channel: Channel) {
        chatService.deleteMessage(channel, message).also { messageDao.delete(message.id) }
    }
}
