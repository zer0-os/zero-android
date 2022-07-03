package com.zero.android.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageReaction(
	var messageId: Long,
	val key: String,
	val userId: String,
	val updatedAt: Long
)
