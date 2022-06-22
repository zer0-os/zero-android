package com.zero.android.common.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel : ViewModel() {

	private val ioJob = SupervisorJob()
	private val mainJob = SupervisorJob()
	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
		throwable.printStackTrace()
	}

	protected val ioScope = CoroutineScope(Dispatchers.IO + ioJob + exceptionHandler)
	protected val uiScope = CoroutineScope(Dispatchers.Main + mainJob + exceptionHandler)

	override fun onCleared() {
		super.onCleared()
		ioJob.cancel()
		mainJob.cancel()
	}
}
