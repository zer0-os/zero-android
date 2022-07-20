package com.zero.android.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.zero.android.models.ChannelCategory
import com.zero.android.models.DirectChannel
import com.zero.android.models.GroupChannel
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.models.enums.AccessType
import com.zero.android.models.enums.AlertType
import com.zero.android.models.enums.ChannelType

@Entity(
	tableName = "channels",
	indices = [Index("networkId"), Index("authorId")],
	foreignKeys =
	[
		ForeignKey(
			entity = NetworkEntity::class,
			parentColumns = ["id"],
			childColumns = ["networkId"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = MemberEntity::class,
			parentColumns = ["id"],
			childColumns = ["authorId"],
			onDelete = ForeignKey.RESTRICT
		)
	]
)
data class ChannelEntity(
	@PrimaryKey override val id: String,
	val lastMessageId: String? = null,
	val authorId: String? = null,
	val memberCount: Int = 0,
	val coverUrl: String? = null,
	val createdAt: Long = 0,
	val isTemporary: Boolean = false,
	val unreadMentionCount: Int = 0,
	val unreadMessageCount: Int = 0,
	val messageLifeSeconds: Int = 0,
	val alerts: AlertType = AlertType.ALL,
	val accessCode: String? = null,
	val networkId: String? = null,
	val category: ChannelCategory? = null,
	val name: String = "",
	val isSuper: Boolean = false,
	val isPublic: Boolean = false,
	val isDiscoverable: Boolean = false,
	val isVideoEnabled: Boolean = false,
	val type: ChannelType = ChannelType.GROUP,
	val isAdminOnly: Boolean = false,
	val telegramChatId: String? = null,
	val discordChatId: String? = null,
	val accessType: AccessType = AccessType.PUBLIC,
	val isDirectChannel: Boolean
) : BaseEntity

fun ChannelEntity.toDirectModel(members: List<Member>, lastMessage: Message? = null) =
	DirectChannel(
		id = id,
		memberCount = memberCount,
		coverUrl = coverUrl,
		createdAt = createdAt,
		isTemporary = isTemporary,
		unreadMentionCount = unreadMentionCount,
		unreadMessageCount = unreadMessageCount,
		messageLifeSeconds = messageLifeSeconds,
		alerts = alerts,
		accessCode = accessCode,
		members = members,
		lastMessage = lastMessage
	)

fun ChannelEntity.toGroupModel(
	members: List<Member>,
	operators: List<Member>,
	createdBy: Member?,
	lastMessage: Message? = null
) =
	GroupChannel(
		id = id,
		memberCount = memberCount,
		coverUrl = coverUrl,
		createdAt = createdAt,
		isTemporary = isTemporary,
		unreadMentionCount = unreadMentionCount,
		unreadMessageCount = unreadMessageCount,
		messageLifeSeconds = messageLifeSeconds,
		alerts = alerts,
		accessCode = accessCode,
		members = members,
		lastMessage = lastMessage,
		networkId = networkId ?: "",
		category = category,
		name = name,
		isSuper = isSuper,
		isPublic = isPublic,
		isDiscoverable = isDiscoverable,
		isVideoEnabled = isVideoEnabled,
		createdBy = createdBy,
		type = type,
		isAdminOnly = isAdminOnly,
		telegramChatId = telegramChatId,
		discordChatId = discordChatId,
		accessType = accessType,
		operators = operators
	)
