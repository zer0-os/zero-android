package com.zero.android.network.services

import com.zero.android.network.model.ApiUser
import com.zero.android.network.util.Response
import retrofit2.http.GET

interface UserService {

	@GET(value = "users/current")
	suspend fun getUser(): Response<ApiUser>
}
