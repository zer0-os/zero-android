package com.zero.android.network.chat.sendbird

import com.sendbird.android.BaseMessage
import com.sendbird.android.GroupChannel
import com.sendbird.android.MessageCollection
import com.sendbird.android.MessageListParams
import com.sendbird.android.MessagePayloadFilter
import com.sendbird.android.ReplyTypeFilter
import com.sendbird.android.SendBirdException
import com.sendbird.android.handlers.MessageCollectionInitHandler
import com.sendbird.android.handlers.MessageCollectionInitPolicy
import com.zero.android.common.extensions.callbackFlowWithAwait
import com.zero.android.network.chat.conversion.toApi
import com.zero.android.network.util.MESSAGES_PAGE_LIMIT

internal class SendBirdMessageService {

	private lateinit var collection: MessageCollection

	fun init(channel: GroupChannel): Boolean {
		if (::collection.isInitialized) {
			if (collection.channel.url == channel.url) return false
			collection.dispose()
		}

		collection =
			MessageCollection.Builder(channel, params()).setStartingPoint(Long.MAX_VALUE).build()

		collection.initialize(
			MessageCollectionInitPolicy.CACHE_AND_REPLACE_BY_API,
			object : MessageCollectionInitHandler {
				override fun onCacheResult(cachedList: List<BaseMessage>?, e: SendBirdException?) {
					// Messages will be retrieved from the local cache.
					// They can be outdated or far from the startingPoint.
				}

				override fun onApiResult(apiResultList: List<BaseMessage>?, e: SendBirdException?) {
					// Messages will be retrieved through API calls from Sendbird server.
					// According to the MessageCollectionInitPolicy.CACHE_AND_REPLACE_BY_API,
					// the existing data source needs to be cleared
					// before adding retrieved messages to the local cache.
				}
			}
		)
		return true
	}

	internal fun params(
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

	private fun hasReachedEnd() = !collection.hasNext()

	private fun loadNext() = callbackFlowWithAwait {
		if (!hasReachedEnd()) {
			collection.loadNext { messages, e ->
				if (e != null) {
					trySend(Pair(hasReachedEnd(), messages.map { it.toApi() }))
				}
			}
		} else {
			trySend(Pair(true, null))
		}
	}
}
