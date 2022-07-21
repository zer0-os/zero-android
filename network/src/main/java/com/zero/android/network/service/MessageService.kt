package com.zero.android.network.service

import com.zero.android.network.model.request.DeleteMessageRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
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
		@Field("content") text: String
	): Response<ResponseBody>

	@FormUrlEncoded
	@DELETE(value = "messages/{id}/deleteChatMessage")
	suspend fun deleteMessage(
		@Path("id") id: String,
		@Body body: DeleteMessageRequest
	): Response<ResponseBody>
}
