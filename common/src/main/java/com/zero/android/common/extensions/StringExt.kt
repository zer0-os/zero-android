package com.zero.android.common.extensions

import android.util.Patterns
import java.net.URL

val String.initials
	get() = this.split(' ').mapNotNull { it.firstOrNull()?.toString() }.reduce { acc, s -> acc + s }

fun String?.initials(): String {
	val text = this?.trim()
	return if (text.isNullOrEmpty()) {
		""
	} else {
		val splits = if (text.contains("-")) text.split("-") else text.split(" ")
		return if (splits.size > 1) {
			"${splits[0].first()}${splits[1].first()}".trim().uppercase()
		} else {
			text.take(2).trim().uppercase()
		}
	}
}

val String.isValidUrl
	get() = Patterns.WEB_URL.matcher(this).matches()

val String.toUrl
	get() = URL(this)
