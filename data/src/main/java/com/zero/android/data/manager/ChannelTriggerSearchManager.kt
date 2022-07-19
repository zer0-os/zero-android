package com.zero.android.data.manager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelTriggerSearchManager @Inject constructor() {

    private val _showSearchBar = MutableStateFlow(false)
    val showSearchBar: StateFlow<Boolean> = _showSearchBar

    suspend fun triggerChannelSearch(show: Boolean) {
        _showSearchBar.emit(show)
    }
}
