package com.zero.android.network.model

import com.zero.android.models.MessageType
import com.zero.android.network.model.serializers.InstantSerializer
import com.zero.android.network.model.serializers.MessageTypeSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiMessage(
	val id: String,
	val feedItemId: String?,
	val channelUrl: String?,
	val author: ApiUser,
	val authorId: String,
	val content: String?,
	val rawContent: String?,
	val url: String?,
	@Serializable(MessageTypeSerializer::class) val mediaType: MessageType,
	@Serializable(InstantSerializer::class) val createdAt: Instant,
	@Serializable(InstantSerializer::class) val updatedAt: Instant,
	val inSync: Boolean,
	val isUploading: Boolean,
	val data: ApiMessageData?,
	val parentMessageText: String?
)

@Serializable
data class ApiMessageData(
	val url: String?,
	val type: String?,
	val width: Float?,
	val height: Float?
)
