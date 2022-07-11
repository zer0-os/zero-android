package com.zero.android.network.model

import com.zero.android.models.enums.ConnectionStatus
import com.zero.android.network.model.serializer.ConnectionStatusSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMember(
	@SerialName("user_id") var id: String,
	@SerialName("nickname") var nickname: String? = null,
	@SerialName("profile_url") var profileUrl: String? = null,
	@SerialName("friend_discovery_key") var friendDiscoveryKey: String? = null,
	@SerialName("friend_name") var friendName: String? = null,
	@SerialName("metadata") var metadata: Map<String, String?>? = null,
	@Serializable(ConnectionStatusSerializer::class)
	var status: ConnectionStatus = ConnectionStatus.NON_AVAILABLE,
	@SerialName("last_seen_at") var lastSeenAt: Long = 0,
	@SerialName("is_active") val isActive: Boolean = true,
	@SerialName("is_blocking_me") val isBlockingMe: Boolean = false,
	@SerialName("ic_blocked_by_me") val isBlockedByMe: Boolean = false,
	@SerialName("is_muted") val isMuted: Boolean = false,
	val profileImage: String? = null
)

@Serializable data class ApiMemberProfile(val profileImage: String? = null)
