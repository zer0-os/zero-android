package com.zero.android.models

import InviteMode

data class Network(
	val id: String,
	val name: String,
	val displayName: String,
	val logo: String?,
	val backgroundImageUrl: String?,
	val lightModeBackgroundImageUrl: String?,
	val isPublic: Boolean,
	val locationShareType: String?,
	val disabledApps: List<String>?,
	val inviteMode: InviteMode,
	val permissions: NetworkPermissions?
)

data class NetworkPermissions(val invite: Boolean)
