package com.zero.android.data.conversion

import com.zero.android.models.GroupChannel
import com.zero.android.models.OpenChannel
import com.zero.android.network.model.ApiGroupChannel
import com.zero.android.network.model.ApiOpenChannel

internal fun ApiOpenChannel.toModel() =
	OpenChannel(
		url = url,
		networkId = networkId,
		name = name,
		operators = operators.map { it.toModel() },
		operatorCount = operatorCount,
		coverUrl = coverUrl,
		createdAt = createdAt,
		data = data,
		isTemporary = isTemporary
	)

internal fun ApiGroupChannel.toModel() =
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
		isTemporary = isTemporary,
		createdBy = createdBy?.toModel(),
		alerts = alerts,
		isPublic = isPublic,
		isDiscoverable = isDiscoverable,
		accessCode = accessCode,
		messageLifeSeconds = messageLifeSeconds
	)
