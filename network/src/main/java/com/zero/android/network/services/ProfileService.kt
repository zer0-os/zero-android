package com.zero.android.network.services

import com.zero.android.network.model.ApiUser
import com.zero.android.network.util.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {

	@GET(value = "profiles/filter")
	suspend fun getProfiles(@Query("filter") filter: String?): Response<ApiUser>
}
