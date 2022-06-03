package com.zero.android.data.conversion

import com.zero.android.models.City
import com.zero.android.network.model.ApiCity

internal fun ApiCity.toModel() = City(id = id, name = name, countryName = countryName)

internal fun ApiCity.toEntity() = toModel()
