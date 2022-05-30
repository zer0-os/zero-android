package com.zero.android.data.conversion

import com.zero.android.database.model.UserEntity
import com.zero.android.models.User
import com.zero.android.network.model.ApiUser

internal fun ApiUser.toModel() =
	User(id = id, profile = profile.toModel(), handle = handle, isNetworkAdmin = isNetworkAdmin)

internal fun ApiUser.toEntity() =
	UserEntity(
		id = id,
		profile = profile.toEntity(),
		handle = handle,
		isNetworkAdmin = isNetworkAdmin
	)
