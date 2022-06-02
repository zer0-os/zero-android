package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable data class ApiCurrency(val id: String, val alphabeticCode: String, val name: String)
