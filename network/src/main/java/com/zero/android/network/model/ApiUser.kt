package com.zero.android.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiUser(
	val id: String,
	val profile: ApiProfile,
	val handle: String,
	@SerialName("isANetworkAdmin") val isNetworkAdmin: Boolean
)
