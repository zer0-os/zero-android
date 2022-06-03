package com.zero.android.network.model

import kotlinx.serialization.Serializable

@Serializable data class ApiWallet(val id: String, val publicAddress: String, val balance: String)
