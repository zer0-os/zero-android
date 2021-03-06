package com.zero.android.network.service

import com.zero.android.network.model.ApiNetwork
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

	@GET(value = "networks/mine/{id}")
	suspend fun getNetworks(@Path("id") userId: String): List<ApiNetwork>
}
