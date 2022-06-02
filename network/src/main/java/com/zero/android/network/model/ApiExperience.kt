package com.zero.android.network.model

import com.zero.android.network.model.serializer.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiExperience(
	val title: String?,
	@Serializable(InstantSerializer::class) val startDate: Instant?,
	@Serializable(InstantSerializer::class) val endDate: Instant?,
	val city: String?,
	val description: String?,
	val organization: ApiOrganization?,
	val isCurrent: Boolean?
)
