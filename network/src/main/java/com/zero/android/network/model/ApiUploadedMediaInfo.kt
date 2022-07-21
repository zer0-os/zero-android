package com.zero.android.network.model

import com.zero.android.models.enums.MessageType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiUploadedMediaInfo(
	@SerialName("api_key") val apiKey: String,
	@SerialName("asset_id") val assetId: String,
	val bytes: Int,
	@SerialName("created_at") val createdAt: String,
	@SerialName("etag") val eTag: String,
	val format: String,
	val height: Int,
	@SerialName("original_filename") val originalFilename: String,
	val placeholder: Boolean,
	@SerialName("public_id") val publicId: String,
	@SerialName("resource_type") val resourceType: String,
	@SerialName("secure_url") val secureUrl: String,
	val signature: String,
	val type: String,
	val url: String,
	val version: Int,
	@SerialName("version_id") val versionId: String,
	val width: Int
) {
	fun getDataMap(type: MessageType): Map<*, *> =
		mutableMapOf<String, Any>(
			"url" to secureUrl,
			"width" to width,
			"height" to height,
			"type" to type.serializedName
		)
}
