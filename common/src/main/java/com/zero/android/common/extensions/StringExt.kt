package com.zero.android.common.extensions

val String.initials
	get() = this.split(' ').mapNotNull { it.firstOrNull()?.toString() }.reduce { acc, s -> acc + s }
