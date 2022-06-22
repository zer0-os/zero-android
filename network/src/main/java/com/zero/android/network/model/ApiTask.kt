package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiTask(
    val id: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val name: String? = null,
    val description: String? = null,
    val coverPhotoURL: String? = null,
    val location: String? = null,
    val walletId: String? = null,
    val discussionId: String? = null,
    val fileKey: String? = null,
    val amount: Double? = null,
    val geofenceRadiusMeters: Int? = null,
    val currency: ApiCurrency? = null,
    val assignments: List<ApiUser>? = null,
    val wallet: ApiWallet? = null,
    private val editPermission: Boolean,
    val geoLockRead: Boolean
)
