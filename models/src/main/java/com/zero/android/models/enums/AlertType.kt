package com.zero.android.models.enums

enum class AlertType(val serializedName: String) {
	ALL("all"),
	OFF("off"),
	MENTION_ONLY("mention_only"),
	DEFAULT("default")
}

fun String?.toAlertType() =
	when (this) {
		null -> AlertType.DEFAULT
		else -> AlertType.values().firstOrNull { type -> type.serializedName == this }
			?: AlertType.DEFAULT
	}
