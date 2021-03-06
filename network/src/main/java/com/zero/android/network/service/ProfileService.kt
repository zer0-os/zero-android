package com.zero.android.network.service

import com.zero.android.network.model.ApiProfile
import com.zero.android.network.model.ApiUser
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {

	@PATCH(value = "profiles/{id}")
	suspend fun updateProfile(@Path("id") id: String): ApiProfile

	@GET(value = "profiles/filter")
	suspend fun getProfiles(@Query("filter") filter: String?): ApiUser
}
