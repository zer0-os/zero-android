package com.zero.android.network.model

import com.zero.android.models.enums.AlertType
import com.zero.android.models.enums.ChannelType
import kotlinx.serialization.Serializable

interface ApiChannel {
	val id: String
	val members: List<ApiMember>
	val memberCount: Int
	val coverUrl: String?
	val createdAt: Long
	val data: String?
	val isTemporary: Boolean
	val unreadMentionCount: Int
	val unreadMessageCount: Int
	val lastMessage: ApiMessage?
	val alerts: AlertType
	val accessCode: String?
}

@Serializable
data class ApiDirectChannel(
	override val id: String,
	override val members: List<ApiMember>,
	override val memberCount: Int,
	override val coverUrl: String? = null,
	override val lastMessage: ApiMessage? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val isTemporary: Boolean = false,
	override val unreadMentionCount: Int = 0,
	override val unreadMessageCount: Int = 0,
	override val alerts: AlertType = AlertType.ALL,
	override val accessCode: String? = null
) : ApiChannel

@Serializable
data class ApiGroupChannel(
	override val id: String,
	val networkId: String? = null,
	val name: String,
	val operators: List<ApiMember>,
	val operatorCount: Int,
	override val members: List<ApiMember>,
	override val memberCount: Int,
	override val coverUrl: String? = null,
	override val lastMessage: ApiMessage? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val isTemporary: Boolean = false,
	val isSuper: Boolean = false,
	val isPublic: Boolean = false,
	val isDiscoverable: Boolean = false,
	val createdBy: ApiMember? = null,
	override val alerts: AlertType = AlertType.ALL,
	val messageLifeSeconds: Int = 0,
	override val accessCode: String? = null,
	val type: ChannelType = ChannelType.GROUP,
	override val unreadMentionCount: Int = 0,
	override val unreadMessageCount: Int = 0
) : ApiChannel
