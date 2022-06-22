package com.zero.android.network.model.serializer

import com.zero.android.models.enums.AlertType
import com.zero.android.models.enums.toAlertType

object AlertTypeSerializer : EnumSerializer<AlertType>() {
	override fun String?.stringToEnum() = toAlertType()
}
