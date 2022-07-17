package com.zero.android.network.chat.sendbird

import com.sendbird.android.MessageListParams
import com.sendbird.android.MessagePayloadFilter
import com.sendbird.android.ReplyTypeFilter
import com.zero.android.common.util.MESSAGES_PAGE_LIMIT

internal object SendBirdMessages {

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
}
