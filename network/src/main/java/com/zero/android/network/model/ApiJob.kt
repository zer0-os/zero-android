package com.zero.android.network.model

import com.zero.android.network.model.serializer.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiJob(
	val id: String,
	val title: String? = null,
	val description: String? = null,
	val coverPhotoURL: String,
	val amount: Double? = null,
	val startDate: String? = null,
	@Serializable(InstantSerializer::class) val endDate: Instant? = null,
	val location: String? = null,
	val actionText: String? = null,
	val interval: String? = null,
	val currency: ApiCurrency? = null
)
