package com.zero.android.models

data class User(
	val id: String,
	val name: String,
	val profile: Profile,
	val isNetworkAdmin: Boolean
)
