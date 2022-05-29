package com.zero.android.network.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ApiExperience(
	val title: String?,
	val startDate: LocalDateTime?,
	val endDate: LocalDateTime?,
	val city: String?,
	val description: String?,
	val organization: ApiOrganization?,
	val organizationId: String?,
	val isCurrent: Boolean?
)
