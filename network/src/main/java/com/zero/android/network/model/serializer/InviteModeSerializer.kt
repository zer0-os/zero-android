package com.zero.android.network.model.serializer

import com.zero.android.models.enums.InviteMode
import com.zero.android.models.enums.toInviteMode

object InviteModeSerializer : EnumSerializer<InviteMode>() {
	override fun String?.stringToEnum() = toInviteMode()
}
