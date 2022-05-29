package com.zero.android.network.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ApiInvestment(
	val round: String?,
	val investmentDate: LocalDateTime?,
	val amount: String?,
	val description: String?,
	val organizationId: String?,
	val organization: ApiOrganization?
)
