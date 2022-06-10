package com.zero.android.models.fake.channel

data class ChannelRowMessage(
    val id: Long,
    val image: String,
    val message: String,
    val description: String,
    val dateTime: String,
    val unreadCount: Int,
    val isVector: Boolean,
    val isDiscord: Boolean
)
