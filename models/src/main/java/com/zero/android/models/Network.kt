package com.zero.android.models

import com.zero.android.models.enums.InviteMode

data class Network(
	val id: String,
	val name: String,
	val displayName: String,
	val logo: String? = null,
	val backgroundImageUrl: String? = null,
	val lightModeBackgroundImageUrl: String? = null,
	val isPublic: Boolean,
	val locationShareType: String? = null,
	val disabledApps: List<String>? = null,
	val inviteMode: InviteMode = InviteMode.NONE,
	val permissions: NetworkPermissions? = null,
	val unreadCount: Int = 0
)

data class NetworkPermissions(val invite: Boolean)
