package com.zero.android.network.chat.sendbird

import com.sendbird.android.FileMessageParams
import com.sendbird.android.SendBird
import com.sendbird.android.UserMessageParams
import com.zero.android.common.extensions.callbackFlowWithAwait
import com.zero.android.common.extensions.withSameScope
import com.zero.android.common.system.Logger
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import com.zero.android.network.chat.ChatListener
import com.zero.android.network.chat.conversion.toApi
import com.zero.android.network.chat.conversion.toParams
import com.zero.android.network.model.ApiMessage
import com.zero.android.network.service.ChatService
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class SendBirdChatService(
	private val logger: Logger,
	private val messages: SendBirdMessages = SendBirdMessages(logger)
) : SendBirdBaseService(), ChatService {

	override suspend fun addListener(channelId: String, listener: ChatListener) {
		SendBird.addChannelHandler(channelId, SendBirdChatListener(listener))
	}

	override suspend fun removeListener(channelId: String) {
		SendBird.removeChannelHandler(channelId)
	}

	override suspend fun getMessages(channel: Channel, timestamp: Long) =
		callbackFlowWithAwait<List<ApiMessage>> {
			val params = messages.listParams(reverse = true)
			getChannel(channel).getMessagesByTimestamp(timestamp, params) { messages, e ->
				if (e != null) {
					logger.e(e)
					throw e
				} else {
					trySend(messages?.map { it.toApi() } ?: emptyList())
				}
			}
		}

	override suspend fun getMessages(channel: Channel, id: String) =
		callbackFlow<List<ApiMessage>> {
			val params = messages.listParams(reverse = true)
			getChannel(channel).getMessagesByMessageId(id.toLong(), params) { messages, e ->
				if (e != null) {
					logger.e(e)
					throw e
				} else {
					trySend(messages?.map { it.toApi() } ?: emptyList())
				}
			}
		}

	override suspend fun send(channel: Channel, message: DraftMessage) = callbackFlowWithAwait {
		val params = message.toParams()
		val sbChannel = getChannel(channel)
		if (params is FileMessageParams) {
			sbChannel.sendFileMessage(params) { fileMessage, e ->
				if (e != null) {
					logger.e("Failed to send file message", e)
					throw e
				}
				trySend(fileMessage.toApi())
			}
		} else if (params is UserMessageParams) {
			sbChannel.sendUserMessage(params) { userMessage, e ->
				if (e != null) {
					logger.e("Failed to send text message", e)
					throw e
				}
				trySend(userMessage.toApi())
			}
		}
	}

	override suspend fun reply(channel: Channel, id: String, message: DraftMessage) =
		send(channel, message.apply { parentMessageId = id })

	override suspend fun deleteMessage(channel: Channel, message: Message) =
		suspendCancellableCoroutine<Unit> { coroutine ->
			withSameScope {
				getChannel(channel).deleteMessage(messages.getMessage(message)) {
					if (it != null) {
						logger.e("Failed to delete message", it)
						coroutine.resumeWithException(it)
					} else {
						coroutine.resume(Unit)
					}
				}
			}
		}
}
