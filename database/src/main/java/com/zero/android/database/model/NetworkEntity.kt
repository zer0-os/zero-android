package com.zero.android.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zero.android.models.Network
import com.zero.android.models.NetworkPermissions
import com.zero.android.models.enums.InviteMode

@Entity(tableName = "networks")
data class NetworkEntity(
	@PrimaryKey val id: String,
	val name: String,
	val displayName: String,
	val logo: String?,
	val backgroundImageUrl: String? = null,
	val lightModeBackgroundImageUrl: String? = null,
	val isPublic: Boolean,
	val locationShareType: String? = null,
	val disabledApps: List<String>? = null,
	val inviteMode: InviteMode,
	@Embedded(prefix = "permissions_") val permissions: NetworkPermissions? = null,
	val unreadCount: Int = 0,
	val isSelected: Boolean = false
)

fun NetworkEntity.toModel() =
	Network(
		id = id,
		name = name,
		displayName = displayName,
		logo = logo,
		backgroundImageUrl = backgroundImageUrl,
		lightModeBackgroundImageUrl = lightModeBackgroundImageUrl,
		isPublic = isPublic,
		locationShareType = locationShareType,
		disabledApps = disabledApps,
		inviteMode = inviteMode,
		permissions = permissions,
		unreadCount = unreadCount
	)
