package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiDeleteMessage(
    val channelId: String,
)
