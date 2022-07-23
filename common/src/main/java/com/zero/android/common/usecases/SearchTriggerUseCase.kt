package com.zero.android.common.usecases

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchTriggerUseCase @Inject constructor(
    private val searchTriggerManager: SearchTriggerManager
) {
    val showSearchBar: StateFlow<Boolean> = searchTriggerManager.showSearchBar

    suspend fun triggerSearch(show: Boolean) {
        if (show) searchTriggerManager.showSearchBar()
        else searchTriggerManager.hideSearchBar()
    }
}
