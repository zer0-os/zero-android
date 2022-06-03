package com.zero.android.database.model

import InviteMode
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zero.android.models.Network
import com.zero.android.models.NetworkPermissions

@Entity(tableName = "networks")
data class NetworkEntity(
	@PrimaryKey val id: String,
	val name: String,
	val displayName: String,
	val logo: String?,
	val backgroundImageUrl: String?,
	val lightModeBackgroundImageUrl: String?,
	val isPublic: Boolean,
	val locationShareType: String?,
	val disabledApps: List<String>?,
	val inviteMode: InviteMode,
	@Embedded(prefix = "permissions_") val permissions: NetworkPermissions?
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
		permissions = permissions
	)
