package com.zero.android.models

import com.zero.android.models.enums.AlertType
import com.zero.android.models.enums.ChannelType

interface Channel {
	val id: String
	val members: List<Member>
	val memberCount: Int
	val coverUrl: String?
	val createdAt: Long
	val data: String?
	val isTemporary: Boolean
	val unreadMentionCount: Int
	val unreadMessageCount: Int
	val lastMessage: Message?
	val messageLifeSeconds: Int
	val alerts: AlertType
	val accessCode: String?
}

data class DirectChannel(
	override val id: String,
	override val members: List<Member>,
	override val memberCount: Int,
	override val coverUrl: String? = null,
	override val lastMessage: Message? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val isTemporary: Boolean = false,
	override val unreadMentionCount: Int = 0,
	override val unreadMessageCount: Int = 0,
	override val messageLifeSeconds: Int = 0,
	override val alerts: AlertType = AlertType.ALL,
	override val accessCode: String? = null
) : Channel

data class GroupChannel(
	override val id: String,
	val networkId: String? = null,
	val name: String,
	val operators: List<Member>,
	val operatorCount: Int,
	override val members: List<Member>,
	override val memberCount: Int,
	override val coverUrl: String? = null,
	override val lastMessage: Message? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val isTemporary: Boolean = false,
	val isSuper: Boolean = false,
	val isPublic: Boolean = false,
	val isDiscoverable: Boolean = false,
	val createdBy: Member? = null,
	override val alerts: AlertType = AlertType.ALL,
	override val messageLifeSeconds: Int = 0,
	override val accessCode: String? = null,
	val type: ChannelType = ChannelType.GROUP,
	override val unreadMentionCount: Int = 0,
	override val unreadMessageCount: Int = 0
) : Channel
