package com.zero.android.common.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.getActivity(): Activity? =
	when (this) {
		is Activity -> this
		is ContextWrapper -> baseContext.getActivity()
		else -> null
	}
