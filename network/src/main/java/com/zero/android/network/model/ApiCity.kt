package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable data class ApiCity(val id: String, val name: String, val countryName: String)
