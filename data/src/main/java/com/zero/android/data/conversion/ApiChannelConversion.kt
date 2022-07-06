package com.zero.android.data.conversion

import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.DirectChannelWithRefs
import com.zero.android.database.model.GroupChannelWithRefs
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
		isTemporary = isTemporary,
		unreadMentionCount = unreadMentionCount,
		alerts = alerts,
		accessCode = accessCode
	)

internal fun ApiGroupChannel.toModel() =
	GroupChannel(
		id = id,
		networkId = networkId,
		category = category,
		name = name,
		isSuper = isSuper,
		operators = operators.map { it.toModel() },
		members = members.map { it.toModel() },
		memberCount = memberCount,
		unreadMentionCount = unreadMentionCount,
		unreadMessageCount = unreadMessageCount,
		lastMessage = lastMessage?.toModel(),
		createdAt = createdAt,
		createdBy = createdBy?.toModel(),
		coverUrl = coverUrl,
		isAdminOnly = properties?.isAdminOnly ?: false,
		telegramChatId = properties?.telegramChatId,
		discordChatId = properties?.discordChatId,
		isTemporary = isTemporary,
		alerts = alerts,
		isPublic = isPublic,
		isDiscoverable = isDiscoverable,
		accessCode = accessCode,
		messageLifeSeconds = messageLifeSeconds,
		type = type,
		accessType = accessType,
		isVideoEnabled = isVideoEnabled
	)

internal fun ApiDirectChannel.toEntity() =
	DirectChannelWithRefs(
		channel =
		ChannelEntity(
			id = id,
			lastMessageId = lastMessage?.id,
			isDirectChannel = true,
			memberCount = memberCount,
			coverUrl = coverUrl,
			createdAt = createdAt,
			isTemporary = isTemporary,
			unreadMentionCount = unreadMentionCount,
			unreadMessageCount = unreadMessageCount,
			alerts = alerts,
			accessCode = accessCode
		),
		members = members.map { it.toEntity() },
		lastMessage = lastMessage?.toEntity()
	)

internal fun ApiGroupChannel.toEntity() =
	GroupChannelWithRefs(
		channel =
		ChannelEntity(
			id = id,
			lastMessageId = lastMessage?.id,
			authorId = createdBy?.id ?: "",
			isDirectChannel = false,
			memberCount = memberCount,
			coverUrl = coverUrl,
			createdAt = createdAt,
			isTemporary = isTemporary,
			unreadMentionCount = unreadMentionCount,
			unreadMessageCount = unreadMessageCount,
			messageLifeSeconds = messageLifeSeconds,
			alerts = alerts,
			accessCode = accessCode,
			networkId = networkId,
			category = category,
			name = name,
			isSuper = isSuper,
			isPublic = isPublic,
			isDiscoverable = isDiscoverable,
			isVideoEnabled = isVideoEnabled,
			type = type,
			isAdminOnly = properties?.isAdminOnly ?: false,
			telegramChatId = properties?.telegramChatId,
			discordChatId = properties?.discordChatId,
			accessType = accessType
		),
		members = members.map { it.toEntity() },
		operators = operators.map { it.toEntity() },
		lastMessage = lastMessage?.toEntity(),
		createdBy = createdBy?.toEntity()
	)
