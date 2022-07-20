package com.zero.android.common.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlin.experimental.ExperimentalTypeInference

inline fun <T> withSameScope(crossinline block: suspend () -> T) =
	CoroutineScope(Dispatchers.Unconfined).launch { block() }

inline fun <T> FlowCollector<T>.emitInScope(
	value: T,
	scope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined)
) = scope.launch { emit(value) }

@OptIn(ExperimentalTypeInference::class)
inline fun <T> callbackFlowWithAwait(
	@BuilderInference crossinline block: suspend ProducerScope<T>.() -> Unit
) = callbackFlow {
	block(this)
	awaitClose()
}
