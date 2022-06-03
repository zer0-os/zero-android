package com.zero.android.data.conversion

import com.zero.android.models.Valuable
import com.zero.android.network.model.ApiValuable

internal fun ApiValuable.toModel() = Valuable(id = id, name = name)

internal fun ApiValuable.toEntity() = toModel()

internal fun List<ApiValuable>.toEntity() = map { it.toEntity() }
