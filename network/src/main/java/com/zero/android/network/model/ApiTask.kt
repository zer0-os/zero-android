package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiTask(
	val id: String,
	val latitude: Double?,
	val longitude: Double?,
	val name: String?,
	val description: String?,
	val coverPhotoURL: String?,
	val location: String?,
	val walletId: String?,
	val discussionId: String?,
	val fileKey: String?,
	val amount: Double?,
	val geofenceRadiusMeters: Int?,
	val currency: ApiCurrency?,
	val assignments: List<ApiUser>?,
	val wallet: ApiWallet?,
	private val editPermission: Boolean,
	val geoLockRead: Boolean
)
