package com.zero.android.network.model

import com.zero.android.models.enums.InviteMode
import com.zero.android.network.model.serializer.InviteModeSerializer
import kotlinx.serialization.Serializable

@Serializable
data class ApiNetwork(
	val id: String,
	val name: String,
	val displayName: String,
	val logo: String? = null,
	val backgroundImageUrl: String? = null,
	val lightModeBackgroundImageUrl: String? = null,
	val isPublic: Boolean,
	val locationShareType: String? = null,
	val disabledApps: List<String>? = null,
	@Serializable(InviteModeSerializer::class) val inviteMode: InviteMode,
	val permissions: ApiNetworkPermissions? = null
)

@Serializable data class ApiNetworkPermissions(val invite: Boolean)
