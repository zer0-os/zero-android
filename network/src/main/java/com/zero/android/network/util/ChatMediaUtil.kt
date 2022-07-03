package com.zero.android.network.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.zero.android.network.model.ApiUploadInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatMediaUtil @Inject constructor(@ApplicationContext private val context: Context) {
	fun getUploadUrl(uploadInfo: ApiUploadInfo) =
		Uri.parse(uploadInfo.apiUrl)
			.buildUpon()
			.appendQueryParameter(QUERY_PARAM_TIMESTAMP, uploadInfo.query?.timestamp?.toString())
			.appendQueryParameter(QUERY_PARAM_SIGNATURE, uploadInfo.query?.signature)
			.appendQueryParameter(QUERY_PARAM_API_KEY, uploadInfo.query?.apikey)
			.build()
			.toString()

	fun getUploadBody(file: File): MultipartBody.Part {
		val mediaType = fileMediaType(file)
		val requestBody = file.asRequestBody(mediaType)
		return MultipartBody.Part.createFormData("file", file.name, requestBody)
	}

	private fun fileMediaType(file: File) =
		getMimeType(Uri.fromFile(file))?.toMediaTypeOrNull() ?: DEFAULT_FILE_MEDIA_TYPE.toMediaType()

	private fun getMimeType(uri: Uri): String? {
		return if (uri.scheme.equals(ContentResolver.SCHEME_CONTENT)) {
			context.contentResolver.getType(uri)
		} else {
			val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
			MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase())
		}
	}

	companion object {
		private const val QUERY_PARAM_TIMESTAMP = "timestamp"
		private const val QUERY_PARAM_SIGNATURE = "signature"
		private const val QUERY_PARAM_API_KEY = "api_key"
		private const val DEFAULT_FILE_MEDIA_TYPE = "image/jpg"
	}
}
