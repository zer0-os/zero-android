package com.zero.android.network.model

import com.zero.android.models.enums.MessageType
import com.zero.android.network.model.serializer.InstantSerializer
import com.zero.android.network.model.serializer.MessageTypeSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiMessage(
	val id: String,
	val feedItemId: String? = null,
	val channelUrl: String? = null,
	val author: ApiUser,
	val authorId: String,
	val content: String? = null,
	val rawContent: String? = null,
	val url: String? = null,
	@Serializable(MessageTypeSerializer::class) val mediaType: MessageType,
	@Serializable(InstantSerializer::class) val createdAt: Instant,
	@Serializable(InstantSerializer::class) val updatedAt: Instant,
	val inSync: Boolean,
	val isUploading: Boolean,
	val data: ApiMessageData? = null,
	val parentMessageText: String? = null
)

@Serializable
data class ApiMessageData(
	val url: String? = null,
	val type: String? = null,
	val width: Float? = null,
	val height: Float?
)
