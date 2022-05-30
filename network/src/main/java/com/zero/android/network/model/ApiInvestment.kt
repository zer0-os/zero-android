package com.zero.android.network.model

import com.zero.android.network.model.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiInvestment(
	val round: String?,
	@Serializable(InstantSerializer::class) val investmentDate: Instant?,
	val amount: String?,
	val description: String?,
	val organizationId: String?,
	val organization: ApiOrganization?
)
