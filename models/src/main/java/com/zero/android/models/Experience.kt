package com.zero.android.models

import kotlinx.datetime.Instant

data class Experience(
    val title: String?,
    val startDate: Instant?,
    val endDate: Instant?,
    val city: String?,
    val description: String?,
    val organization: Organization?,
    val isCurrent: Boolean?
)
