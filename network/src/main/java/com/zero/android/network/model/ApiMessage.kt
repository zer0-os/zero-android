package com.zero.android.network.model

import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import com.zero.android.network.model.serializer.MessageMentionTypeSerializer
import com.zero.android.network.model.serializer.MessageStatusSerializer
import com.zero.android.network.model.serializer.MessageTypeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMessage(
	val id: String,
	@SerialName("channel_url") val channelId: String,
	val author: ApiMember,
	val mentions: List<ApiMember> = emptyList(),
	@Serializable(MessageTypeSerializer::class) val type: MessageType,
	@Serializable(MessageMentionTypeSerializer::class) val mentionType: MessageMentionType,
	@SerialName("created_at") val createdAt: Long,
	@SerialName("updated_at") val updatedAt: Long,
	@Serializable(MessageStatusSerializer::class) val status: MessageStatus,
	val message: String? = null,
	val data: String? = null,
	val parentMessage: ApiMessage? = null,
	val isMuted: Boolean = false,
	val fileUrl: String? = null,
	val fileName: String? = null,
	val fileThumbnails: List<ApiFileThumbnail>? = null,
	val fileMimeType: String? = null,
	val reactions: List<ApiMessageReaction>? = null
)

@Serializable
data class ApiFileThumbnail(
	var maxWidth: Int = 0,
	val maxHeight: Int = 0,
	val realWidth: Int = 0,
	val realHeight: Int = 0,
	val url: String? = null
)

@Serializable
data class ApiFileData(
	@SerialName("height") val height: Int,
	@SerialName("type") val type: String,
	@SerialName("url") val url: String,
	@SerialName("width") val width: Int
)

@Serializable
data class ApiMessageReaction(
	var messageId: Long,
	val key: String,
	val userId: String,
	val updatedAt: Long
)
