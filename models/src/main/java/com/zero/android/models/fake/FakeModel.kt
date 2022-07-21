package com.zero.android.models.fake

import com.zero.android.models.DirectChannel
import com.zero.android.models.GroupChannel
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.models.Network
import com.zero.android.models.enums.ConnectionStatus
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType

object FakeModel {

	fun Network(id: String = "id") =
		Network(
			id = id,
			name = "display.name",
			displayName = "Display Name",
			isPublic = true,
			unreadCount = 4
		)

	fun networks() = listOf(Network("one"), Network("two"), Network("three"))

	fun DirectChannel(id: String = "url", unread: Int = 1) =
		DirectChannel(
			id = id,
			createdAt = 0,
			memberCount = 2,
			members = listOf(Member("one"), Member("two")),
			unreadMessageCount = unread
		)

	fun GroupChannel(id: String = "url", name: String = "Channel Name", unread: Int = 1) =
		GroupChannel(
			id = id,
			name = name,
			networkId = "",
			createdAt = 0,
			memberCount = 2,
			members = listOf(Member("one"), Member("two")),
			operators = listOf(Member("one")),
			unreadMessageCount = unread
		)

	fun groupChannels() = listOf(GroupChannel("one"), GroupChannel("two"))

	fun directChannels() = listOf(DirectChannel("one"), DirectChannel("two"))

	fun Member(
		id: String = "id",
		name: String = "Member Name",
		status: ConnectionStatus = ConnectionStatus.OFFLINE
	) = Member(id = id, name = name, status = status, isActive = true)

	fun Message(
		id: String = "id",
		channelId: String = "id",
		status: MessageStatus = MessageStatus.SUCCEEDED
	) =
		Message(
			id = id,
			channelId = channelId,
			author = Member(id = "authorId"),
			createdAt = 0,
			updatedAt = 0,
			status = status,
			type = MessageType.TEXT
		)

	fun DraftMessage(channelId: String = "id", status: MessageStatus = MessageStatus.SUCCEEDED) =
		com.zero.android.models.DraftMessage(
			channelId = channelId,
			author = Member(id = "authorId"),
			createdAt = 0,
			updatedAt = 0,
			status = status,
			type = MessageType.TEXT
		)
}
