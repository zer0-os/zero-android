package com.zero.android.models

import com.zero.android.common.util.WEB_URL
import kotlinx.datetime.Instant

data class Job(
    val id: String,
    val title: String?,
    val description: String?,
    val coverPhotoURL: String,
    val amount: Double?,
    val startDate: String?,
    val endDate: Instant?,
    val location: String?,
    val actionText: String?,
    val interval: String?,
    val currency: Currency?
) {

    fun isSubscription() = interval != null

    fun purchaseLink(): String? {
        if (amount == null) return null
        val objectType = if (isSubscription()) "subscriptionItem" else "item"
        return "$WEB_URL/m/$objectType/$id"
    }
}
