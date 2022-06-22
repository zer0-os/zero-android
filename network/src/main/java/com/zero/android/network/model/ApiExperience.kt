package com.zero.android.network.model

import com.zero.android.network.model.serializer.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiExperience(
    val title: String? = null,
    @Serializable(InstantSerializer::class) val startDate: Instant? = null,
    @Serializable(InstantSerializer::class) val endDate: Instant? = null,
    val city: String? = null,
    val description: String? = null,
    val organization: ApiOrganization? = null,
    val isCurrent: Boolean? = null
)
