package com.zero.android.data.conversion

import com.zero.android.database.model.UserEntity
import com.zero.android.models.User
import com.zero.android.network.model.ApiUser

internal fun ApiUser.toModel() =
    User(id = id, profile = profile.toModel(), name = name, isNetworkAdmin = isNetworkAdmin)

internal fun ApiUser.toEntity() = UserEntity(id = id, name = name, isNetworkAdmin = isNetworkAdmin)
