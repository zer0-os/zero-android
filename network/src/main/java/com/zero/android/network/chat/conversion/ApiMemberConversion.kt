/* ktlint-disable filename */
package com.zero.android.network.chat.conversion

import com.zero.android.models.Member
import com.zero.android.network.model.ApiMember

internal fun Member.toApi() =
    ApiMember(
        id = id,
        nickname = name,
        profileUrl = profileUrl,
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
