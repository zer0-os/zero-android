package com.zero.android.data.conversion

import com.zero.android.models.Experience
import com.zero.android.network.model.ApiExperience

internal fun ApiExperience.toModel() =
    Experience(
        title = title,
        startDate = startDate,
        endDate = endDate,
        city = city,
        description = description,
        organization = organization?.toEntity(),
        isCurrent = isCurrent
    )

internal fun ApiExperience.toEntity() = toModel()

internal fun List<ApiExperience>.toEntity() = map { it.toEntity() }
