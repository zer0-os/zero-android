package com.zero.android.common.extensions

import java.util.concurrent.TimeUnit

fun Int.convertDurationToString() =
	String.format(
		"%02d:%02d",
		TimeUnit.MILLISECONDS.toMinutes(this.toLong()),
		TimeUnit.MILLISECONDS.toSeconds(this.toLong()) % TimeUnit.MINUTES.toSeconds(1)
	)
