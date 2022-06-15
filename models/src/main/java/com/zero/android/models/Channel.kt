package com.zero.android.models

import com.zero.android.models.enums.AlertType

interface Channel {
	val url: String
	val networkId: String?
	val name: String
	val operators: List<Member>
	val operatorCount: Int
	val coverUrl: String?
	val createdAt: Long
	val data: String?
	val isTemporary: Boolean
}

data class OpenChannel(
	override val url: String,
	override val networkId: String? = null,
	override val name: String,
	override val operators: List<Member>,
	override val operatorCount: Int,
	override val coverUrl: String? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val isTemporary: Boolean = false
) : Channel

data class GroupChannel(
	override val url: String,
	override val networkId: String? = null,
	override val name: String,
	override val operators: List<Member>,
	override val operatorCount: Int,
	val members: List<Member>,
	val memberCount: Int,
	val unreadMentionCount: Int = 0,
	val unreadMessageCount: Int = 0,
	override val coverUrl: String? = null,
	val lastMessage: Message? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val isTemporary: Boolean = false,
	val isSuper: Boolean = false,
	val isPublic: Boolean = false,
	val isDiscoverable: Boolean = false,
	val createdBy: Member? = null,
	val alerts: AlertType = AlertType.ALL,
	val messageLifeSeconds: Int = 0,
	val accessCode: String? = null
) : Channel
