package com.zero.android.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class AuthCredentials(
    val idToken: String,
    val accessToken: String,
    val type: String,
    val refreshToken: String?,
    val expiresAt: Instant
)
