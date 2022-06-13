package com.zero.android.network.model

import com.sendbird.android.Member
import com.sendbird.android.Sender
import com.sendbird.android.User
import com.sendbird.android.User.ConnectionStatus
import kotlinx.serialization.Serializable

@Serializable
data class ApiMember(
	var id: String,
	var nickname: String? = null,
	var profileUrl: String? = null,
	var friendDiscoveryKey: String? = null,
	var friendName: String? = null,
	var metaData: Map<String?, String?>? = null,
	var connectionStatus: ConnectionStatus? = null,
	var lastSeenAt: Long = 0,
	val isActive: Boolean = true,
	val isBlockingMe: Boolean = false,
	val isBlockedByMe: Boolean = false,
	val isMuted: Boolean = false
)

internal fun User.toApi() =
	ApiMember(
		id = userId,
		nickname = nickname,
		profileUrl = profileUrl,
		friendDiscoveryKey = friendDiscoveryKey,
		friendName = friendName,
		metaData = metaData,
		connectionStatus = connectionStatus,
		lastSeenAt = lastSeenAt,
		isActive = isActive
	)

internal fun Member.toApi() =
	ApiMember(
		id = userId,
		nickname = nickname,
		profileUrl = profileUrl,
		friendDiscoveryKey = friendDiscoveryKey,
		friendName = friendName,
		metaData = metaData,
		connectionStatus = connectionStatus,
		lastSeenAt = lastSeenAt,
		isActive = isActive,
		isBlockedByMe = isBlockedByMe,
		isBlockingMe = isBlockingMe,
		isMuted = isMuted
	)

internal fun Sender.toApi() =
	ApiMember(
		id = userId,
		nickname = nickname,
		profileUrl = profileUrl,
		friendDiscoveryKey = friendDiscoveryKey,
		friendName = friendName,
		metaData = metaData,
		connectionStatus = connectionStatus,
		lastSeenAt = lastSeenAt,
		isActive = isActive,
		isBlockedByMe = isBlockedByMe
	)
