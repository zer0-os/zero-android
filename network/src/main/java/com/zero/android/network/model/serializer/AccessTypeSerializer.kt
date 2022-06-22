package com.zero.android.network.model.serializer

import com.zero.android.models.enums.AccessType
import com.zero.android.models.enums.toAccessType

object AccessTypeSerializer : EnumSerializer<AccessType>() {
	override fun String?.stringToEnum() = toAccessType()
}
