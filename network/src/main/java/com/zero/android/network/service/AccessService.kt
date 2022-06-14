package com.zero.android.network.service

import com.zero.android.network.model.ApiChatAccessToken
import retrofit2.http.Body
import retrofit2.http.POST

interface AccessService {

	@POST(value = "accounts/sync")
	suspend fun getChatAccessToken(@Body body: HashMap<String, String>): ApiChatAccessToken
}
