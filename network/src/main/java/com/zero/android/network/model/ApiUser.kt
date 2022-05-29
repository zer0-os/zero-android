package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiUser(
	val id: String,
	val profileId: String,
	val handle: String,
	val isNetworkAdmin: Boolean,
	val profile: ApiProfile
)
