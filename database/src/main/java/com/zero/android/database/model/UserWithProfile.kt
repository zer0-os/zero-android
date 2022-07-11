package com.zero.android.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.zero.android.models.User

data class UserWithProfile(
	@Embedded val user: UserEntity,
	@Relation(parentColumn = "id", entityColumn = "userId") val profile: ProfileEntity
)

fun UserWithProfile.toModel() =
	User(
		id = user.id,
		profile = profile.toModel(),
		name = user.name,
		isNetworkAdmin = user.isNetworkAdmin
	)
