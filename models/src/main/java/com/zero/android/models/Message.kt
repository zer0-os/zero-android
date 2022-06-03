package com.zero.android.models

import com.zero.android.models.enums.MessageType
import kotlinx.datetime.Instant

data class ApiMessage(
	val id: String,
	val feedItemId: String?,
	val channelUrl: String?,
	val author: User,
	val authorId: String,
	val content: String?,
	val rawContent: String?,
	val url: String?,
	val mediaType: MessageType,
	val createdAt: Instant,
	val updatedAt: Instant,
	val inSync: Boolean,
	val isUploading: Boolean,
	val data: MessageData?,
	val parentMessageText: String?
)

data class MessageData(val url: String?, val type: String?, val width: Float?, val height: Float?)
