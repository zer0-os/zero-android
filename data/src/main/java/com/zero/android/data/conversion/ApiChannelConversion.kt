package com.zero.android.data.conversion

import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel
import com.zero.android.models.OpenChannel
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiGroupChannel
import com.zero.android.network.model.ApiOpenChannel

internal fun ApiChannel.toChannel(): Channel {
	if (this is ApiOpenChannel) return this.toChannel()
	else if (this is ApiGroupChannel) return this.toChannel()
	throw IllegalStateException("not handled")
}

internal fun ApiOpenChannel.toChannel() =
	OpenChannel(
		url = url,
		networkId = networkId,
		name = name,
		operators = operators.map { it.toModel() },
		operatorCount = operatorCount,
		coverUrl = coverUrl,
		createdAt = createdAt,
		data = data,
		customType = customType,
		type = type,
		isTemporary = isTemporary
	)

internal fun ApiGroupChannel.toChannel() =
	GroupChannel(
		url = url,
		networkId = networkId,
		name = name,
		isSuper = isSuper,
		operators = operators.map { it.toModel() },
		operatorCount = operatorCount,
		members = members.map { it.toModel() },
		memberCount = memberCount,
		unreadMentionCount = unreadMentionCount,
		unreadMessageCount = unreadMessageCount,
		lastMessage = lastMessage?.toModel(),
		createdAt = createdAt,
		coverUrl = coverUrl,
		data = data,
		customType = customType,
		type = type,
		isTemporary = isTemporary,
		createdBy = createdBy?.toModel(),
		alerts = alerts,
		isPublic = isPublic,
		isDiscoverable = isDiscoverable
	)
