package com.zero.android.data.conversion

import com.zero.android.models.Organization
import com.zero.android.network.model.ApiOrganization

internal fun ApiOrganization.toModel() =
	Organization(
		id = id,
		name = name,
		avatarURL = avatarURL,
		backgroundImageURL = backgroundImageURL,
		summary = summary
	)

internal fun ApiOrganization.toEntity() = toModel()
