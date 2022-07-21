package com.zero.android.network.model.fake

import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import com.zero.android.network.model.ApiMember
import com.zero.android.network.model.ApiMessage

object FakeApi {

	fun Message(id: String = "123", channelId: String = "channelId") =
		ApiMessage(
			id = id,
			author = Member("authorId"),
			channelId = channelId,
			createdAt = 0,
			updatedAt = 0,
			mentionType = MessageMentionType.UNKNOWN,
			status = MessageStatus.SUCCEEDED,
			type = MessageType.TEXT
		)

	fun Member(id: String = "id") = ApiMember(id = id)
}
