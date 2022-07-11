package com.zero.android.database.model.fake

import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.DirectChannelWithRefs
import com.zero.android.database.model.GroupChannelWithRefs
import com.zero.android.database.model.MemberEntity
import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageWithRefs
import com.zero.android.database.model.NetworkEntity
import com.zero.android.models.ChannelCategory
import com.zero.android.models.enums.InviteMode
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType

@Suppress("TestFunctionName")
object FakeEntity {

	fun NetworkEntity(id: String = "networkId", name: String = "name") =
		NetworkEntity(
			id = id,
			name = name,
			displayName = "name",
			logo = "",
			isPublic = false,
			inviteMode = InviteMode.NONE
		)

	fun DirectChannelWithRefs(
		id: String = "directChannelId",
		lastMessage: MessageWithRefs? = MessageWithRefs(id = "directLastMessageId", channelId = id)
	) =
		DirectChannelWithRefs(
			channel =
			ChannelEntity(
				id = id,
				lastMessageId = lastMessage?.message?.id,
				isDirectChannel = true,
				memberCount = 2
			),
			members = listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberTwo")),
			lastMessage = lastMessage
		)

	fun GroupChannelWithRefs(
		id: String = "groupChannelId",
		networkId: String = "networkId",
		category: ChannelCategory = "category",
		lastMessage: MessageWithRefs? = MessageWithRefs(id = "groupLastMessageId", channelId = id)
	) =
		GroupChannelWithRefs(
			createdBy = MemberEntity(id = "memberFive"),
			channel =
			ChannelEntity(
				id = id,
				networkId = networkId,
				lastMessageId = lastMessage?.message?.id,
				isDirectChannel = false,
				memberCount = 2,
				category = category
			),
			members = listOf(MemberEntity(id = "memberFive"), MemberEntity(id = "memberFour")),
			operators = listOf(MemberEntity(id = "memberFive")),
			lastMessage = lastMessage
		)

	fun MessageWithRefs(
		id: String = "messageId",
		channelId: String = "channelId",
		authorId: String = "memberOne"
	) =
		MessageWithRefs(
			message =
			MessageEntity(
				id = id,
				authorId = authorId,
				parentMessageId = "parentMessageId",
				parentMessageAuthorId = "memberThree",
				channelId = channelId,
				type = MessageType.TEXT,
				status = MessageStatus.PENDING
			),
			author = MemberEntity(id = authorId),
			parentMessage =
			MessageEntity(
				id = "parentMessageId",
				authorId = "memberThree",
				channelId = channelId,
				type = MessageType.TEXT,
				status = MessageStatus.PENDING
			),
			parentMessageAuthor = MemberEntity(id = "memberThree"),
			mentions = listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberTwo"))
		)
}
