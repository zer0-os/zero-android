package com.zero.android.models.fake

import com.zero.android.models.GroupChannel
import com.zero.android.models.Member
import com.zero.android.models.Network
import com.zero.android.models.enums.ConnectionStatus

object FakeData {

	fun Network(id: String = "id") =
		Network(
			id = id,
			name = "display.name",
			displayName = "Display Name",
			isPublic = true,
			unreadCount = 4
		)

	fun networks() = listOf(Network("one"), Network("two"), Network("three"))

	fun Channel(url: String = "url", name: String = "Channel Name", unread: Int = 1) =
		GroupChannel(
			url = url,
			name = name,
			createdAt = 0,
			memberCount = 2,
			members = listOf(Member("one"), Member("two")),
			operatorCount = 1,
			operators = listOf(Member("one")),
			unreadMessageCount = unread
		)

	fun channels() = listOf(Channel("one"), Channel("two"))

	fun Member(
		id: String = "id",
		name: String = "Member Name",
		status: ConnectionStatus = ConnectionStatus.OFFLINE
	) = Member(id = id, name = name, status = status, isActive = true)
}
