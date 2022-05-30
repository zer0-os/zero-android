package com.zero.android.network.model

import com.zero.android.network.model.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiJob(
	val id: String,
	val title: String?,
	val description: String?,
	val coverPhotoURL: String,
	val amount: Double?,
	val startDate: String?,
	@Serializable(InstantSerializer::class) val endDate: Instant?,
	val location: String?,
	val actionText: String?,
	val interval: String?,
	val currency: ApiCurrency?
)
