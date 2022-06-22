package com.zero.android.models

data class MessageReaction(
    var messageId: Long,
    val key: String,
    val userId: String,
    val updatedAt: Long
)
