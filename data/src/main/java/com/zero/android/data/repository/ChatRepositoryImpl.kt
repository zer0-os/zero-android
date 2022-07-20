package com.zero.android.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zero.android.common.util.MESSAGES_PAGE_LIMIT
import com.zero.android.data.conversion.toEntity
import com.zero.android.data.conversion.toModel
import com.zero.android.data.repository.chat.MessageListener
import com.zero.android.data.repository.chat.MessagesRemoteMediator
import com.zero.android.database.dao.MessageDao
import com.zero.android.database.model.toModel
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageType
import com.zero.android.network.service.ChatMediaService
import com.zero.android.network.service.ChatService
import com.zero.android.network.service.MessageService
import com.zero.android.network.util.NetworkMediaUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class ChatRepositoryImpl
@Inject
constructor(
	private val chatService: ChatService,
	private val chatMediaService: ChatMediaService,
	private val networkMediaUtil: NetworkMediaUtil,
	private val messageDao: MessageDao,
	private val messageService: MessageService
) : ChatRepository {

	override val channelChatMessages = MutableStateFlow<PagingData<Message>>(PagingData.empty())

	override suspend fun addListener(id: String) =
		chatService.addListener(id, MessageListener(messageDao))

	override suspend fun removeListener(id: String) = chatService.removeListener(id)

	override suspend fun getMessages(channel: Channel, lastMessageId: String): Flow<List<Message>> {
		return chatService.getMessages(channel, lastMessageId).map { messages ->
			messages.map { it.toModel() }
		}
	}

	@OptIn(ExperimentalPagingApi::class)
	override suspend fun getMessages(channel: Channel, timestamp: Long): Flow<PagingData<Message>> {
		return Pager(
			config = PagingConfig(pageSize = MESSAGES_PAGE_LIMIT),
			remoteMediator = MessagesRemoteMediator(chatService, messageDao),
			pagingSourceFactory = { messageDao.getByChannel(channel.id) }
		)
			.flow
			.map { data -> data.map { it.toModel() } }
			.apply { collect(channelChatMessages) }
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
				DraftMessage(
					channelId = channel.id,
					author = message.author,
					type = message.type,
					mentionType = message.mentionType,
					fileUrl = fileUpload.secureUrl,
					fileName = fileUpload.originalFilename,
					fileMimeType = fileUpload.type,
					createdAt = message.createdAt,
					updatedAt = message.updatedAt,
					status = message.status,
					data = JSONObject(fileUpload.dataMap).toString()
				)
			} else {
				Timber.e("Upload Info is required for file upload")
				message
			}
		return chatService.send(channel, fileMessage).map { it.toModel() }
	}

	override suspend fun reply(channel: Channel, id: String, message: DraftMessage): Flow<Message> {
		return chatService.reply(channel, id, message).map {
			messageDao.upsert(it.toEntity())
			it.toModel()
		}
	}

	override suspend fun updateMessage(id: String, channelId: String, text: String) {
		messageService.updateMessage(id, channelId, text).firstOrNull()?.let {
			messageDao.upsert(it.toEntity())
		}
	}

	override suspend fun deleteMessage(id: String, channelId: String) {
		return messageService.deleteMessage(id, channelId).also { messageDao.delete(id) }
	}
}
