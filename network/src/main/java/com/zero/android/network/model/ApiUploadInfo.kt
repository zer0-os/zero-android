package com.zero.android.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiUploadInfo(
    val apiUrl: String,
    val query: ApiUploadQuery? = null
)

@Serializable
data class ApiUploadQuery(
    val timestamp: Long,
    val signature: String,
    @SerialName("api_key")
    val apikey: String,
)
