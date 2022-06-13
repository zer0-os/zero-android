package com.zero.android.network.model

import com.sendbird.android.BaseMessage
import com.sendbird.android.BaseMessage.SendingStatus
import com.sendbird.android.BaseMessageParams.MentionType
import com.sendbird.android.FileMessage
import com.sendbird.android.UserMessage
import com.zero.android.models.enums.*
import com.zero.android.network.model.serializer.MessageMentionTypeSerializer
import com.zero.android.network.model.serializer.MessageStatusSerializer
import com.zero.android.network.model.serializer.MessageTypeSerializer
import kotlinx.serialization.Serializable

@Serializable
data class ApiMessage(
	val id: Long,
	val feedItemId: String? = null,
	val channelUrl: String? = null,
	val author: ApiMember,
	val mentions: List<ApiMember> = emptyList(),
	@Serializable(MessageTypeSerializer::class) val type: MessageType,
	@Serializable(MessageMentionTypeSerializer::class) val mentionType: MessageMentionType,
	val createdAt: Long,
	val updatedAt: Long,
	@Serializable(MessageStatusSerializer::class) val status: MessageStatus,
	val data: String? = null,
	val parentMessage: ApiMessage? = null,
	val isMuted: Boolean = false,
	val fileUrl: String? = null,
	val fileName: String? = null,
	val fileThumbnails: List<ApiFileThumbnail>? = null
)

@Serializable
data class ApiFileThumbnail(
	var maxWidth: Int = 0,
	val maxHeight: Int = 0,
	val realWidth: Int = 0,
	val realHeight: Int = 0,
	val url: String? = null
)

internal fun BaseMessage.toApi() =
	ApiMessage(
		id = messageId,
		type = customType.toMessageType(),
		mentionType = mentionType.toType(),
		channelUrl = channelUrl,
		author = sender.toApi(),
		status = sendingStatus.toType(),
		createdAt = createdAt,
		updatedAt = updatedAt
	)

internal fun UserMessage.toApi() =
	ApiMessage(
		id = messageId,
		channelUrl = channelUrl,
		author = sender.toApi(),
		mentions = mentionedUsers.map { it.toApi() },
		type = customType.toMessageType(),
		mentionType = mentionType.toType(),
		createdAt = createdAt,
		updatedAt = updatedAt,
		status = sendingStatus.toType(),
		data = data,
		parentMessage = parentMessage?.toApi(),
		isMuted = isSilent
	)

internal fun FileMessage.toApi() =
	ApiMessage(
		id = messageId,
		channelUrl = channelUrl,
		author = sender.toApi(),
		mentions = mentionedUsers.map { it.toApi() },
		type = type.toMessageType(),
		mentionType = mentionType.toType(),
		createdAt = createdAt,
		updatedAt = updatedAt,
		status = sendingStatus.toType(),
		data = data,
		parentMessage = parentMessage?.toApi(),
		isMuted = isSilent,
		fileUrl = url,
		fileName = name,
		fileThumbnails = thumbnails.map { it.toApi() }
	)

internal fun FileMessage.Thumbnail.toApi() =
	ApiFileThumbnail(
		maxWidth = maxWidth,
		maxHeight = maxHeight,
		realWidth = realWidth,
		realHeight = realHeight,
		url = url
	)

internal fun MentionType.toType() = value.toMessageMentionType()

internal fun MessageMentionType.toType() = MentionType.valueOf(serializedName)

internal fun SendingStatus.toType() = value.toMessageStatus()

internal fun MessageStatus.toStatus() = MessageStatus.valueOf(serializedName)
