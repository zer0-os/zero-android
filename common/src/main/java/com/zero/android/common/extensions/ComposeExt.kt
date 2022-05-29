package com.zero.android.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleOwner.OnEvent(observer: LifecycleEventObserver) {
	DisposableEffect(this) {
		lifecycle.addObserver(observer)
		onDispose { lifecycle.removeObserver(observer) }
	}
}
