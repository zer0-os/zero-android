package com.zero.android.network.model

import com.sendbird.android.GroupChannel
import com.sendbird.android.OpenChannel
import com.sendbird.android.SendBird
import com.zero.android.models.enums.AlertType
import com.zero.android.models.enums.ChannelType
import com.zero.android.models.enums.toAlertType
import kotlinx.serialization.Serializable

interface ApiChannel {
	val name: String
	val members: List<ApiMember>
	val memberCount: Int
	val channelUrl: String
	val coverUrl: String?
	val createdAt: Long
	val data: String?
	val customType: String?
	val type: ChannelType
	val isTemporary: Boolean
}

@Serializable
data class ApiOpenChannel(
	override val name: String,
	override val members: List<ApiMember>,
	override val memberCount: Int,
	override val channelUrl: String,
	override val coverUrl: String? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val customType: String? = null,
	override val type: ChannelType,
	override val isTemporary: Boolean = false
) : ApiChannel

@Serializable
data class ApiGroupChannel(
	override val name: String,
	override val members: List<ApiMember>,
	override val memberCount: Int,
	val unreadMentionCount: Int,
	val unreadMessageCount: Int,
	override val channelUrl: String,
	override val coverUrl: String? = null,
	val lastMessage: ApiMessage? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val customType: String? = null,
	override val type: ChannelType,
	override val isTemporary: Boolean = false,
	val isSuper: Boolean = false,
	val isPublic: Boolean = false,
	val createdBy: ApiMember? = null,
	val alerts: AlertType = AlertType.ALL
) : ApiChannel

internal fun OpenChannel.toApi() =
	ApiOpenChannel(
		name = name,
		members = operators.map { it.toApi() },
		memberCount = participantCount,
		channelUrl = url,
		createdAt = createdAt,
		coverUrl = coverUrl,
		data = data,
		customType = customType,
		type = ChannelType.OPEN,
		isTemporary = isEphemeral
	)

internal fun GroupChannel.toApi() =
	ApiGroupChannel(
		name = name,
		isSuper = isSuper,
		members = this.members.map { it.toApi() },
		memberCount = memberCount,
		unreadMentionCount = unreadMentionCount,
		unreadMessageCount = unreadMessageCount,
		channelUrl = url,
		lastMessage = lastMessage.toApi(),
		createdAt = createdAt,
		coverUrl = coverUrl,
		data = data,
		customType = customType,
		type = ChannelType.OPEN,
		isTemporary = isEphemeral,
		createdBy = creator.toApi(),
		alerts = myPushTriggerOption.toType()
	)

internal fun GroupChannel.PushTriggerOption.toType() = name.lowercase().toAlertType()

internal fun SendBird.PushTriggerOption.toType() = value.toAlertType()
