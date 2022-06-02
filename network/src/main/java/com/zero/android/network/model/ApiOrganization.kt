package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiOrganization(
	val id: String,
	val name: String,
	val avatarURL: String?,
	val backgroundImageURL: String?,
	val summary: String?
)
