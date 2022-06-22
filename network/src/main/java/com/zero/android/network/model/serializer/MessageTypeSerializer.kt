package com.zero.android.network.model.serializer

import com.zero.android.models.enums.MessageType
import com.zero.android.models.enums.toMessageType

object MessageTypeSerializer : EnumSerializer<MessageType>() {
    override fun String?.stringToEnum() = toMessageType()
}
