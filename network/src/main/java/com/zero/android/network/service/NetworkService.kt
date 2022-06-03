package com.zero.android.network.service

import com.zero.android.network.model.ApiNetwork
import com.zero.android.network.util.Response
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NetworkService {

	@PATCH(value = "networks/mine/{id}")
	suspend fun getNetworks(@Path("id") userId: String): Response<List<ApiNetwork>>
}
