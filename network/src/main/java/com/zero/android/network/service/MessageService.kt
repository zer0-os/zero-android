package com.zero.android.network.service

import com.zero.android.network.model.ApiDeleteMessage
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MessageService {

	@FormUrlEncoded
	@PUT(value = "messages/{id}/editChatMessage")
	suspend fun updateMessage(
		@Path("id") id: String,
		@Field("channelId") channelId: String,
		@Field("content") text: String
	): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "messages/{id}/deleteChatMessage", hasBody = true)
    suspend fun deleteMessage(@Path("id") id: String, @Body body: ApiDeleteMessage): Response<ResponseBody>
    //@DELETE(value = "messages/{id}/deleteChatMessage")
    //suspend fun deleteMessage(@Path("id") id: String, @Field("channelId") channelId: String)
}
