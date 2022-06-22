package com.zero.android.network.service

import kotlinx.serialization.json.JsonObject
import retrofit2.http.GET

interface AccountService {

	@GET(value = "accounts/sync")
	suspend fun sync(): JsonObject
}
