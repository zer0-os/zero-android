enum class InviteMode(val serializedName: String) {
	NONE("none"),
	UNLIMITED("unlimitedUse")
}

fun String?.toInviteMode() =
	when (this) {
		null -> InviteMode.NONE
		else -> InviteMode.values().firstOrNull { type -> type.serializedName == this }
			?: InviteMode.NONE
	}
