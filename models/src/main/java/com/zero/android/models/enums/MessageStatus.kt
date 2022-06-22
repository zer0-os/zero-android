package com.zero.android.models.enums

enum class MessageStatus(val serializedName: String) {
    NONE("none"),
    PENDING("pending"),
    FAILED("failed"),
    SUCCEEDED("succeeded"),
    CANCELED("canceled")
}

fun String?.toMessageStatus() =
    when (this) {
        null -> MessageStatus.NONE
        else -> MessageStatus.values().firstOrNull { type -> type.serializedName == this }
            ?: MessageStatus.NONE
    }
