package com.zero.android.network.service

import com.zero.android.network.model.ApiUploadInfo
import com.zero.android.network.model.ApiUploadedMediaInfo
import okhttp3.MultipartBody
import retrofit2.http.*

interface ChatMediaService {

	@GET("upload/info")
	suspend fun getUploadInfo(): ApiUploadInfo

	@Multipart
	@POST
	suspend fun uploadMediaFile(
		@Url url: String,
		@Part part: MultipartBody.Part
	): ApiUploadedMediaInfo
}
