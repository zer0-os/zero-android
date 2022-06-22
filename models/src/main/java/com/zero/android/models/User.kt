package com.zero.android.models

import com.zero.android.models.enums.ConnectionStatus

data class User(
    val id: String,
    val name: String,
    val profile: Profile,
    val isNetworkAdmin: Boolean
)

data class Member(
    val id: String,
    val name: String? = null,
    var profileUrl: String? = null,
    val profileImage: String? = null,
    var friendDiscoveryKey: String? = null,
    var friendName: String? = null,
    var metadata: Map<String?, String?>? = null,
    var status: ConnectionStatus = ConnectionStatus.NON_AVAILABLE,
    var lastSeenAt: Long = 0,
    val isActive: Boolean = true,
    val isBlockingMe: Boolean = false,
    val isBlockedByMe: Boolean = false,
    val isMuted: Boolean = false
)
