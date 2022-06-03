package com.zero.android.network.model.serializer

import InviteMode
import toInviteMode

object InviteModeSerializer : EnumSerializer<InviteMode>() {
	override fun String?.stringToEnum() = toInviteMode()
}
