package com.zero.android.common.usecases

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchTriggerManager @Inject constructor() {

    private val _showSearchBar = MutableStateFlow(false)
    val showSearchBar: StateFlow<Boolean> = _showSearchBar

    suspend fun showSearchBar() {
        _showSearchBar.emit(true)
    }

    suspend fun hideSearchBar() {
        _showSearchBar.emit(false)
    }
}
