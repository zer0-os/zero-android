package com.zero.android.system.logger

import com.zero.android.common.system.Logger
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ConsoleLogger @Inject constructor() : Logger {

	override fun setup(debug: Boolean) {
		Timber.plant(if (debug) Timber.DebugTree() else Timber.asTree())
	}

	override fun v(message: String) = Timber.v(message)

	override fun v(t: Throwable, message: String?) = Timber.v(t, message)

	override fun d(message: String) = Timber.d(message)

	override fun d(t: Throwable, message: String?) = Timber.d(t, message)

	override fun i(message: String) = Timber.i(message)

	override fun i(t: Throwable, message: String?) = Timber.i(t, message)

	override fun w(message: String) = Timber.w(message)

	override fun w(t: Throwable, message: String?) = Timber.w(t, message)

	override fun e(message: String, t: Throwable?) = Timber.e(message, t)

	override fun e(t: Throwable) = Timber.e(t)
}
