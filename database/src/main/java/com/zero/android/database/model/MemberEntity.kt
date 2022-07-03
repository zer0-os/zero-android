package com.zero.android.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zero.android.models.Member
import com.zero.android.models.enums.ConnectionStatus

@Entity(tableName = "members")
data class MemberEntity(
	@PrimaryKey val id: String,
	val name: String? = null,
	var profileUrl: String? = null,
	val profileImage: String? = null,
	var friendDiscoveryKey: String? = null,
	var friendName: String? = null,
	var metadata: Map<String, String?>? = null,
	var status: ConnectionStatus = ConnectionStatus.NON_AVAILABLE,
	var lastSeenAt: Long = 0,
	val isActive: Boolean = true,
	val isBlockingMe: Boolean = false,
	val isBlockedByMe: Boolean = false,
	val isMuted: Boolean = false
)

fun MemberEntity.toModel() =
	Member(
		id = id,
		name = name,
		profileUrl = profileUrl,
		profileImage = profileImage,
		friendDiscoveryKey = friendDiscoveryKey,
		friendName = friendName,
		metadata = metadata,
		status = status,
		lastSeenAt = lastSeenAt,
		isActive = isActive,
		isBlockingMe = isBlockingMe,
		isBlockedByMe = isBlockedByMe,
		isMuted = isMuted
	)
