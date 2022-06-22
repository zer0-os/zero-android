package com.zero.android.models

data class Organization(
    val id: String,
    val name: String,
    val avatarURL: String?,
    val backgroundImageURL: String?,
    val summary: String?
)
