package com.zero.android.network.chat.conversion

import com.sendbird.android.BaseChannel
import com.sendbird.android.GroupChannel
import com.sendbird.android.GroupChannelParams
import com.sendbird.android.Member
import com.sendbird.android.OpenChannel
import com.sendbird.android.OpenChannelParams
import com.sendbird.android.SendBird
import com.zero.android.models.Channel
import com.zero.android.models.DirectChannel
import com.zero.android.models.enums.ChannelType
import com.zero.android.models.enums.toAlertType
import com.zero.android.models.enums.toChannelType
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiChannelProperties
import com.zero.android.network.model.ApiDirectChannel
import com.zero.android.network.model.ApiGroupChannel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun BaseChannel.toApi(): ApiChannel {
	if (this is OpenChannel) return toApi() else if (this is GroupChannel) return toApi()
	throw IllegalStateException("not handled")
}

private fun getNetworkId(customType: String): String? {
	return Regex(pattern = "network:([-a-zA-Z0-9]+)").matchEntire(customType)?.value
}

internal fun String.encodeToNetworkId() = "network:$this"

internal val OpenChannel.networkId
	get() = customType?.let { getNetworkId(it) }
internal val GroupChannel.networkId
	get() = customType?.let { getNetworkId(it) }

private val GroupChannel.isDirectChannel
	get() = networkId == null

internal fun Channel.isGroupChannel() =
	this is DirectChannel ||
		(this is com.zero.android.models.GroupChannel && type == ChannelType.GROUP)

internal fun Channel.isOpenChannel() =
	this is com.zero.android.models.GroupChannel && type == ChannelType.OPEN

internal fun OpenChannel.toApi() =
	ApiGroupChannel(
		id = url,
		networkId = networkId,
		name = name,
		members = operators.map { it.toApi() },
		memberCount = participantCount,
		operators = operators.map { it.toApi() },
		operatorCount = participantCount,
		createdAt = createdAt,
		coverUrl = coverUrl,
		properties = Json.decodeFromString<ApiChannelProperties>(data),
		isTemporary = isEphemeral
	)

internal fun GroupChannel.toApi() = if (isDirectChannel) toDirectApi() else toGroupApi()

internal fun GroupChannel.toDirectApi(): ApiDirectChannel {
	return ApiDirectChannel(
		id = url,
		members = members.map { it.toApi() },
		memberCount = memberCount,
		unreadMentionCount = unreadMentionCount,
		unreadMessageCount = unreadMessageCount,
		lastMessage = lastMessage.toApi(),
		createdAt = createdAt,
		coverUrl = coverUrl,
		properties = Json.decodeFromString<ApiChannelProperties>(data),
		isTemporary = isEphemeral,
		alerts = myPushTriggerOption.toType()
	)
}

internal fun GroupChannel.toGroupApi(): ApiGroupChannel {
	val operators = members.filter { it.role == Member.Role.OPERATOR }.map { it.toApi() }
	return ApiGroupChannel(
		id = url,
		networkId = networkId,
		name = name,
		isSuper = isSuper,
		operators = operators,
		operatorCount = operators.size,
		members = members.map { it.toApi() },
		memberCount = memberCount,
		unreadMentionCount = unreadMentionCount,
		unreadMessageCount = unreadMessageCount,
		lastMessage = lastMessage.toApi(),
		createdAt = createdAt,
		coverUrl = coverUrl,
		properties = Json.decodeFromString<ApiChannelProperties>(data),
		isTemporary = isEphemeral,
		createdBy = creator.toApi(),
		alerts = myPushTriggerOption.toType(),
		isPublic = isPublic && isDiscoverable,
		isDiscoverable = isDiscoverable
	)
}

internal fun DirectChannel.toParams() =
	GroupChannelParams().apply {
		setName(members.map { it.name }.joinToString(","))
		setChannelUrl(id)
		setCoverUrl(coverUrl)
		setData(Json.encodeToString(toProperties()))
		setCustomType(null)

		setSuper(false)
		setPublic(false)
		setDiscoverable(false)
		setEphemeral(isTemporary)
		setStrict(false)
		setAccessCode(null)
		setMessageSurvivalSeconds(messageLifeSeconds)
	}

internal fun com.zero.android.models.GroupChannel.toOpenParams() =
	OpenChannelParams().apply {
		setName(name)
		setChannelUrl(id)
		setCoverUrl(coverUrl)
		setData(Json.encodeToString(toProperties()))
		setCustomType(networkId?.encodeToNetworkId())
	}

internal fun com.zero.android.models.GroupChannel.toParams() =
	GroupChannelParams().apply {
		setName(name)
		setChannelUrl(id)
		setCoverUrl(coverUrl)
		setData(Json.encodeToString(toProperties()))
		setCustomType(networkId?.encodeToNetworkId())

		setSuper(isSuper)
		setPublic(isPublic)
		setDiscoverable(isDiscoverable)
		setEphemeral(isTemporary)
		setStrict(false)
		setAccessCode(accessCode)
		setMessageSurvivalSeconds(messageLifeSeconds)
	}

private fun Channel.toProperties() =
	ApiChannelProperties(
		isAdminOnly = isAdminOnly,
		telegramChatId = telegramChatId,
		discordChatId = discordChatId
	)

internal fun BaseChannel.ChannelType.toType() = value().toChannelType()

internal fun GroupChannel.PushTriggerOption.toType() = name.lowercase().toAlertType()

internal fun SendBird.PushTriggerOption.toType() = value.toAlertType()
