package com.zero.android.network.model.serializer

import com.zero.android.models.enums.ConnectionStatus
import com.zero.android.models.enums.toConnectionStatus

object ConnectionStatusSerializer : EnumSerializer<ConnectionStatus>() {
    override fun String?.stringToEnum() = toConnectionStatus()
}
