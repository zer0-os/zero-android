package com.zero.android.models.enums

enum class ConnectionStatus(val serializedName: String) {
	NON_AVAILABLE("non_available"),
	ONLINE("online"),
	OFFLINE("offline"),
	UNKNOWN("")
}

fun String?.toConnectionStatus() =
	when (this) {
		null -> ConnectionStatus.NON_AVAILABLE
		else -> ConnectionStatus.values().firstOrNull { type -> type.serializedName == this }
			?: ConnectionStatus.NON_AVAILABLE
	}
