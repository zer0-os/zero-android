package com.zero.android.network.model

import com.zero.android.models.enums.AlertType
import kotlinx.serialization.Serializable

interface ApiChannel {
	val url: String
	val networkId: String?
	val name: String
	val operators: List<ApiMember>
	val operatorCount: Int
	val coverUrl: String?
	val createdAt: Long
	val data: String?
	val isTemporary: Boolean
}

@Serializable
data class ApiOpenChannel(
	override val url: String,
	override val networkId: String? = null,
	override val name: String,
	override val operators: List<ApiMember>,
	override val operatorCount: Int,
	override val coverUrl: String? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val isTemporary: Boolean = false
) : ApiChannel

@Serializable
data class ApiGroupChannel(
	override val url: String,
	override val networkId: String? = null,
	override val name: String,
	override val operators: List<ApiMember>,
	override val operatorCount: Int,
	val members: List<ApiMember>,
	val memberCount: Int,
	val unreadMentionCount: Int,
	val unreadMessageCount: Int,
	override val coverUrl: String? = null,
	val lastMessage: ApiMessage? = null,
	override val createdAt: Long,
	override val data: String? = null,
	override val isTemporary: Boolean = false,
	val isSuper: Boolean = false,
	val isPublic: Boolean = false,
	val isDiscoverable: Boolean = false,
	val createdBy: ApiMember? = null,
	val alerts: AlertType = AlertType.ALL,
	val messageLifeSeconds: Int = 0,
	val accessCode: String? = null
) : ApiChannel
