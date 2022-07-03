/* ktlint-disable filename */
package com.zero.android.data.conversion

import com.zero.android.models.Member
import com.zero.android.network.model.ApiMember

internal fun ApiMember.toModel() =
	Member(
		id = id,
		name = nickname,
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
