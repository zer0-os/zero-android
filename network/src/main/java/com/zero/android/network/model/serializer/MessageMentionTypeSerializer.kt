package com.zero.android.network.model.serializer

import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.toMessageMentionType

object MessageMentionTypeSerializer : EnumSerializer<MessageMentionType>() {
	override fun String?.stringToEnum() = toMessageMentionType()
}
