package com.zero.android.network.model.serializer

import com.zero.android.models.enums.ChannelType
import com.zero.android.models.enums.toChannelType

object ChannelTypeSerializer : EnumSerializer<ChannelType>() {
	override fun String?.stringToEnum() = toChannelType()
}
