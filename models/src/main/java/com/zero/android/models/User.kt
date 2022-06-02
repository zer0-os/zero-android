package com.zero.android.models

data class User(
	val id: String,
	val profile: Profile,
	val handle: String,
	val isNetworkAdmin: Boolean
)
