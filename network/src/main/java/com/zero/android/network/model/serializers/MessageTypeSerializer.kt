package com.zero.android.network.model.serializers

import com.zero.android.models.MessageType
import com.zero.android.models.toMessageType

object MessageTypeSerializer : EnumSerializer<MessageType>() {
	override fun String?.stringToEnum() = toMessageType()
}
