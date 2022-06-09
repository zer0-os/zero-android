package com.zero.android.common.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

	protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
		throwable.printStackTrace()
	}
}
