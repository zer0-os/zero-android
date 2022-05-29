package com.zero.android.network.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ApiEducation(
	val startDate: LocalDateTime?,
	val endDate: LocalDateTime?,
	val degree: String?,
	val field: String?,
	val description: String?,
	val organization: ApiOrganization?,
	val organizationId: String?
)
