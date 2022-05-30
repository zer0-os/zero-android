package com.zero.android.models

data class User(
	val id: String,
	val profileId: String,
	val handle: String,
	val isNetworkAdmin: Boolean,
	val profile: Profile
)
