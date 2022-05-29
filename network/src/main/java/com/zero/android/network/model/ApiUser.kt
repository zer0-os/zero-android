package com.zero.android.network.model

class ApiUser(
	val id: String,
	val profileId: String,
	val handle: String,
	val isNetworkAdmin: Boolean,
	val profile: ApiProfile
)
