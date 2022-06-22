package com.zero.android.data.conversion

import com.zero.android.models.Education
import com.zero.android.network.model.ApiEducation

internal fun ApiEducation.toModel() =
    Education(
        startDate = startDate,
        endDate = endDate,
        degree = degree,
        field = field,
        description = description,
        organization = organization?.toEntity()
    )

internal fun ApiEducation.toEntity() = toModel()

internal fun List<ApiEducation>.toEntity() = map { it.toEntity() }
