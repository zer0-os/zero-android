package com.zero.android.network.chat.conversion

import android.util.Base64
import com.sendbird.android.Member
import com.sendbird.android.Sender
import com.sendbird.android.User
import com.zero.android.models.enums.ConnectionStatus
import com.zero.android.models.enums.toConnectionStatus
import com.zero.android.network.model.ApiMember
import com.zero.android.network.model.ApiMemberProfile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import okio.internal.commonAsUtf8ToByteArray

private fun ApiMember.toSendBirdBase64(): ByteArray {
	val json =
		Json.encodeToJsonElement(this).run {
			jsonObject.apply { plus(Pair("is_online", status == ConnectionStatus.ONLINE)) }
			Json.encodeToString(this)
		}
	return Base64.decode(json.commonAsUtf8ToByteArray(), 0)
}

internal fun User.toApi() =
	ApiMember(
		id = userId,
		nickname = nickname,
		profileUrl = profileUrl,
		friendDiscoveryKey = friendDiscoveryKey,
		friendName = friendName,
		metadata = metaData,
		status = connectionStatus.toType(),
		lastSeenAt = lastSeenAt,
		isActive = isActive
	)

internal fun ApiMember.toUser() = User.buildFromSerializedData(toSendBirdBase64())

internal fun Member.toApi(): ApiMember {
	val properties =
		if (profileUrl.isNullOrBlank()) ApiMemberProfile(null)
		else Json { ignoreUnknownKeys = true }.decodeFromString<ApiMemberProfile?>(profileUrl)
	return ApiMember(
		id = userId,
		nickname = nickname,
		profileUrl = profileUrl,
		profileImage = properties?.profileImage,
		friendDiscoveryKey = friendDiscoveryKey,
		friendName = friendName,
		metadata = metaData,
		status = connectionStatus.toType(),
		lastSeenAt = lastSeenAt,
		isActive = isActive,
		isBlockedByMe = isBlockedByMe,
		isBlockingMe = isBlockingMe,
		isMuted = isMuted
	)
}

internal fun ApiMember.toMember() = Member.buildFromSerializedData(toSendBirdBase64())

internal fun Sender.toApi(): ApiMember {
	val properties =
		if (profileUrl.isNullOrBlank()) ApiMemberProfile(null)
		else Json { ignoreUnknownKeys = true }.decodeFromString<ApiMemberProfile?>(profileUrl)
	return ApiMember(
		id = userId,
		nickname = nickname,
		profileUrl = profileUrl,
		profileImage = properties?.profileImage,
		friendDiscoveryKey = friendDiscoveryKey,
		friendName = friendName,
		metadata = metaData,
		status = connectionStatus.toType(),
		lastSeenAt = lastSeenAt,
		isActive = isActive,
		isBlockedByMe = isBlockedByMe
	)
}

internal fun ApiMember.toSender() = Sender.buildFromSerializedData(toSendBirdBase64())

internal fun User.ConnectionStatus.toType() = name.lowercase().toConnectionStatus()

internal fun ConnectionStatus.toStatus() = User.ConnectionStatus.valueOf(serializedName.uppercase())
