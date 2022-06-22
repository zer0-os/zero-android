package com.zero.android.models.enums

enum class AccessType(val serializedName: String) {
	PUBLIC("public"),
	PRIVATE("private")
}

fun String?.toAccessType() =
	when (this) {
		null -> AccessType.PRIVATE
		else -> AccessType.values().firstOrNull { type -> type.serializedName == this }
			?: AccessType.PRIVATE
	}
