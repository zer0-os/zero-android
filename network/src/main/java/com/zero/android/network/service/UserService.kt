package com.zero.android.network.service

import com.zero.android.network.model.ApiChatAccessToken
import com.zero.android.network.model.ApiUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

	@GET(value = "users/current")
	suspend fun getUser(): ApiUser

	@POST(value = "accounts/sync")
	suspend fun getChatAccessToken(@Body body: HashMap<String, String>): ApiChatAccessToken
}
