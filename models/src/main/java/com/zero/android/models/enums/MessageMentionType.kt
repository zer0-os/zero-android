package com.zero.android.models.enums

enum class MessageMentionType(val serializedName: String) {
    USER("users"),
    CHANNEL("channel"),
    UNKNOWN("")
}

fun String?.toMessageMentionType() =
    when (this) {
        null -> MessageMentionType.UNKNOWN
        else -> MessageMentionType.values().firstOrNull { type -> type.serializedName == this }
            ?: MessageMentionType.UNKNOWN
    }
