package com.zero.android.network.chat.conversion

import com.sendbird.android.*
import com.zero.android.models.enums.toAlertType
import com.zero.android.models.enums.toChannelType
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiGroupChannel
import com.zero.android.network.model.ApiOpenChannel

internal fun BaseChannel.toApi(): ApiChannel {
	if (this is OpenChannel) return toApi() else if (this is GroupChannel) return toApi()
	throw IllegalStateException("not handled")
}

private fun getNetworkId(customType: String): String? {
	return Regex(pattern = "network:([-a-zA-Z0-9]+)").matchEntire(customType)?.value
}

internal fun String.encodeToNetworkId() = "network:$this"

internal val OpenChannel.networkId: String?
	get() = customType?.let { getNetworkId(it) }
internal val GroupChannel.networkId: String?
	get() = customType?.let { getNetworkId(it) }

internal fun OpenChannel.toApi() =
	ApiOpenChannel(
		url = url,
		networkId = networkId,
		name = name,
		operators = operators.map { it.toApi() },
		operatorCount = participantCount,
		createdAt = createdAt,
		coverUrl = coverUrl,
		data = data,
		isTemporary = isEphemeral
	)

internal fun GroupChannel.toApi(): ApiGroupChannel {
	val operators = members.filter { it.role == Member.Role.OPERATOR }.map { it.toApi() }
	return ApiGroupChannel(
		url = url,
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
		data = data,
		isTemporary = isEphemeral,
		createdBy = creator.toApi(),
		alerts = myPushTriggerOption.toType(),
		isPublic = isPublic && isDiscoverable,
		isDiscoverable = isDiscoverable
	)
}

internal fun com.zero.android.models.OpenChannel.toParams() =
	OpenChannelParams().apply {
		setName(name)
		setChannelUrl(url)
		setCoverUrl(coverUrl)
		setData(data)
		setCustomType(networkId?.encodeToNetworkId())
	}

internal fun com.zero.android.models.GroupChannel.toParams() =
	GroupChannelParams().apply {
		setName(name)
		setChannelUrl(url)
		setCoverUrl(coverUrl)
		setData(data)
		setCustomType(networkId?.encodeToNetworkId())

		setSuper(isSuper)
		setPublic(isPublic)
		setDiscoverable(isDiscoverable)
		setEphemeral(isTemporary)
		setStrict(false)
		setAccessCode(accessCode)
		setMessageSurvivalSeconds(messageLifeSeconds)
	}

internal fun BaseChannel.ChannelType.toType() = value().toChannelType()

internal fun GroupChannel.PushTriggerOption.toType() = name.lowercase().toAlertType()

internal fun SendBird.PushTriggerOption.toType() = value.toAlertType()
