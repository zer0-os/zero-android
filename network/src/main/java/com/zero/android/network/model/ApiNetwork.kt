package com.zero.android.network.model

import InviteMode
import com.zero.android.network.model.serializer.InviteModeSerializer
import kotlinx.serialization.Serializable

@Serializable
data class ApiNetwork(
	val id: String,
	val name: String,
	val displayName: String,
	val logo: String?,
	val backgroundImageUrl: String?,
	val lightModeBackgroundImageUrl: String?,
	val isPublic: Boolean,
	val locationShareType: String?,
	val disabledApps: List<String>?,
	@Serializable(InviteModeSerializer::class) val inviteMode: InviteMode,
	val permissions: ApiNetworkPermissions?
)

@Serializable data class ApiNetworkPermissions(val invite: Boolean)
