package com.zero.android.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.zero.android.models.User

@Entity(
	tableName = "users",
	foreignKeys =
	[
		ForeignKey(
			entity = ProfileEntity::class,
			parentColumns = ["id"],
			childColumns = ["profile"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class UserEntity(
	@PrimaryKey val id: String,
	val profile: ProfileEntity,
	val handle: String,
	val isNetworkAdmin: Boolean
)

fun UserEntity.toModel() =
	User(id = id, profile = profile.toModel(), handle = handle, isNetworkAdmin = isNetworkAdmin)
