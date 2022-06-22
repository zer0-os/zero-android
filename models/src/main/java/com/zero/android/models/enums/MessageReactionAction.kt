package com.zero.android.models.enums

enum class MessageReactionAction(val serializedName: String) {
	ADD("add"),
	DELETE("delete")
}

fun String.toMessageReactionAction() =
	MessageReactionAction.values().first { type -> type.serializedName == this }
