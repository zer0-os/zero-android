package com.zero.android.data.conversion

import com.zero.android.models.DirectChannel
import com.zero.android.models.GroupChannel
import com.zero.android.network.model.ApiDirectChannel
import com.zero.android.network.model.ApiGroupChannel

internal fun ApiDirectChannel.toModel() =
	DirectChannel(
		id = id,
		members = members.map { it.toModel() },
		memberCount = memberCount,
		coverUrl = coverUrl,
		lastMessage = lastMessage?.toModel(),
		createdAt = createdAt,
		data = data,
		isTemporary = isTemporary,
		unreadMentionCount = unreadMentionCount,
		alerts = alerts,
		accessCode = accessCode
	)

internal fun ApiGroupChannel.toModel() =
	GroupChannel(
		id = id,
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
		createdBy = createdBy?.toModel(),
		coverUrl = coverUrl,
		data = data,
		isTemporary = isTemporary,
		alerts = alerts,
		isPublic = isPublic,
		isDiscoverable = isDiscoverable,
		accessCode = accessCode,
		messageLifeSeconds = messageLifeSeconds,
		type = type
	)
