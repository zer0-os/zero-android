package com.zero.android.data.conversion

import com.zero.android.models.Investment
import com.zero.android.network.model.ApiInvestment

internal fun ApiInvestment.toModel() =
    Investment(
        round = round,
        investmentDate = investmentDate,
        amount = amount,
        description = description,
        organization = organization?.toEntity()
    )

internal fun ApiInvestment.toEntity() = toModel()

internal fun List<ApiInvestment>.toEntity() = map { it.toEntity() }
