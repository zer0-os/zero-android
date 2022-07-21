package com.zero.android.network.chat.sendbird

import com.sendbird.android.BaseMessage
import com.sendbird.android.FileMessage
import com.sendbird.android.MessageListParams
import com.sendbird.android.MessagePayloadFilter
import com.sendbird.android.ReplyTypeFilter
import com.sendbird.android.UserMessage
import com.zero.android.common.system.Logger
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageType
import com.zero.android.network.chat.conversion.toApi
import com.zero.android.network.chat.conversion.toParams
import com.zero.android.network.util.MESSAGES_PAGE_LIMIT
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class SendBirdMessages(private val logger: Logger) {

	fun listParams(
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

	suspend fun getMessage(message: Message) =
		suspendCancellableCoroutine<BaseMessage> { coroutine ->
			if (message.type == MessageType.TEXT) {
				UserMessage.getMessage(message.toApi().toParams()) { baseMessage, e ->
					if (e != null) {
						logger.e(e)
						coroutine.resumeWithException(e)
					} else {
						coroutine.resume(baseMessage)
					}
				}
			} else {
				FileMessage.getMessage(message.toApi().toParams()) { baseMessage, e ->
					if (e != null) {
						logger.e(e)
						coroutine.resumeWithException(e)
					} else {
						coroutine.resume(baseMessage)
					}
				}
			}
		}
}
