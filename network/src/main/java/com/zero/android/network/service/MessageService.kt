package com.zero.android.network.service

import com.zero.android.network.model.ApiMessage
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT
import retrofit2.http.Path

interface MessageService {

    @FormUrlEncoded
    @PUT(value = "messages/{id}/editChatMessage")
    suspend fun updateMessage(
        @Path("id") id: String,
        @Field("channelId") channelId: String,
        @Field("text") text: String
    ): Flow<ApiMessage>

    @FormUrlEncoded
    @DELETE(value = "messages/{id}/deleteChatMessage")
    suspend fun deleteMessage(@Path("id") id: String, @Field("channelId") channelId: String)
}
