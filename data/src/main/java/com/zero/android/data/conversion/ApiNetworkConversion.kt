package com.zero.android.data.conversion

import com.zero.android.database.model.NetworkEntity
import com.zero.android.models.Network
import com.zero.android.models.NetworkPermissions
import com.zero.android.network.model.ApiNetwork
import com.zero.android.network.model.ApiNetworkPermissions

internal fun ApiNetwork.toModel() =
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
        permissions = permissions?.toModel()
    )

internal fun ApiNetwork.toEntity() =
    NetworkEntity(
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
        permissions = permissions?.toModel()
    )

internal fun ApiNetworkPermissions.toModel() = NetworkPermissions(invite = invite)
