package com.zero.android.models

import kotlinx.datetime.Instant

data class Education(
    val startDate: Instant?,
    val endDate: Instant?,
    val degree: String?,
    val field: String?,
    val description: String?,
    val organization: Organization?
)
