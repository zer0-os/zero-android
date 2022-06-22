package com.zero.android.network.service

import com.zero.android.network.model.ApiChatAccessToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccessService {

    @FormUrlEncoded
    @POST(value = "accounts/sync")
    suspend fun getChatAccessToken(@Field("idToken") token: String): ApiChatAccessToken
}
