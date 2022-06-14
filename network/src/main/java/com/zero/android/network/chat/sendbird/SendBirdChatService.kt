package com.zero.android.network.chat.sendbird

import com.sendbird.android.*
import com.zero.android.common.extensions.emitInScope
import com.zero.android.common.extensions.withSameScope
import com.zero.android.common.system.Logger
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import com.zero.android.network.chat.ChatListener
import com.zero.android.network.chat.conversion.toApi
import com.zero.android.network.chat.conversion.toMessage
import com.zero.android.network.chat.conversion.toParams
import com.zero.android.network.service.ChatService
import com.zero.android.network.util.MESSAGES_PAGE_LIMIT
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class SendBirdChatService(private val logger: Logger) :
	SendBirdBaseService(), ChatService {

	private fun messageListParams(
		reverse: Boolean = true,
		limit: Int = MESSAGES_PAGE_LIMIT,
		replyFilter: ReplyTypeFilter = ReplyTypeFilter.ALL
	) =
		MessageListParams().apply {
			previousResultSize = limit
			nextResultSize = limit
			isInclusive = true
			setReverse(reverse)
			replyTypeFilter = replyFilter
			messagePayloadFilter =
				MessagePayloadFilter.Builder()
					.setIncludeParentMessageInfo(true)
					.setIncludeThreadInfo(true)
					.build()
		}

	override suspend fun receiveMessages(id: String, listener: ChatListener) {
		SendBird.addChannelHandler(id, SendBirdChatListener(listener))
	}

	override suspend fun getMessages(channel: Channel, timestamp: Long) = flow {
		val params = messageListParams(reverse = true)
		getChannel(channel).getMessagesByMessageId(timestamp, params) { messages, e ->
			if (e != null) {
				logger.e(e)
				throw e
			} else {
				emitInScope(messages?.map { it.toApi() } ?: emptyList())
			}
		}
	}

	override suspend fun getMessagesTill(channel: Channel, id: String) = flow {
		val params = messageListParams(reverse = true)
		getChannel(channel).getMessagesByMessageId(id.toLong(), params) { messages, e ->
			if (e != null) {
				logger.e(e)
				throw e
			} else {
				emitInScope(messages?.map { it.toApi() } ?: emptyList())
			}
		}
	}

	override suspend fun sendMessage(channel: Channel, message: DraftMessage) = flow {
		val params = message.toParams()
		val sbChannel = getChannel(channel)
		if (params is FileMessageParams) {
			sbChannel.sendFileMessage(params) { fileMessage, e ->
				if (e != null) {
					logger.e("Failed to send file message", e)
					throw e
				}
				emitInScope(fileMessage.toApi())
			}
		} else if (params is UserMessageParams) {
			sbChannel.sendUserMessage(params) { userMessage, e ->
				if (e != null) {
					logger.e("Failed to send text message", e)
					throw e
				}
				emitInScope(userMessage.toApi())
			}
		}
	}

	override suspend fun replyMessage(channel: Channel, id: String, message: DraftMessage) =
		sendMessage(channel, message.apply { parentMessageId = id })

	override suspend fun deleteMessage(channel: Channel, message: Message) =
		suspendCancellableCoroutine<Unit> { coroutine ->
			withSameScope {
				getChannel(channel).deleteMessage(message.toApi().toMessage()) {
					if (it != null) {
						logger.e("Failed to send text message", it)
						coroutine.resumeWithException(it)
					} else {
						coroutine.resume(Unit)
					}
				}
			}
		}
}
