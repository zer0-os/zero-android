package com.zero.android.common.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

inline fun <T> withSameScope(crossinline block: suspend () -> T) =
	CoroutineScope(Dispatchers.Unconfined).launch { block() }

inline fun <T> FlowCollector<T>.emitInScope(value: T) =
	CoroutineScope(Dispatchers.Unconfined).launch { emit(value) }
