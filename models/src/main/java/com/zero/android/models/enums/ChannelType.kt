package com.zero.android.models.enums

enum class ChannelType(val serializedName: String) {
    OPEN("open"),
    GROUP("group")
}

fun String?.toChannelType() =
    when (this) {
        null -> ChannelType.OPEN
        else -> ChannelType.values().firstOrNull { type -> type.serializedName == this }
            ?: ChannelType.OPEN
    }
