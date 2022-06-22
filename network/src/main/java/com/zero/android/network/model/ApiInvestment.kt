package com.zero.android.network.model

import com.zero.android.network.model.serializer.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiInvestment(
	val round: String? = null,
	@Serializable(InstantSerializer::class) val investmentDate: Instant? = null,
	val amount: String? = null,
	val description: String? = null,
	val organization: ApiOrganization? = null
)
