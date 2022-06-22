package com.zero.android.network.model.serializer

import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.toMessageStatus

object MessageStatusSerializer : EnumSerializer<MessageStatus>() {
    override fun String?.stringToEnum() = toMessageStatus()
}
