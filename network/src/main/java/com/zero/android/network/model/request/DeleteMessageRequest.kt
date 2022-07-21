package com.zero.android.network.model.request

import kotlinx.serialization.Serializable

@Serializable data class DeleteMessageRequest(val channelId: String)
