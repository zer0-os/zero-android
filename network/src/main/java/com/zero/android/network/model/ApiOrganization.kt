package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiOrganization(
	val id: String,
	val name: String,
	val avatarURL: String? = null,
	val backgroundImageURL: String? = null,
	val summary: String? = null
)
