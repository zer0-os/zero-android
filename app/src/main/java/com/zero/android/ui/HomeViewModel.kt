package com.zero.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zero.android.common.navigation.NavDestination
import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.data.repository.NetworkRepository
import com.zero.android.feature.feed.navigation.FeedDestination
import com.zero.android.models.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
	ViewModel() {

	val currentScreen: NavDestination = FeedDestination

	val selectedNetwork = MutableSharedFlow<Network>(replay = 1)
	val networks = MutableStateFlow<Result<List<Network>>>(Result.Loading)

	init {
		loadNetworks()
	}

	private fun loadNetworks() {
		CoroutineScope(Dispatchers.IO).launch {
			networkRepository.getNetworks().asResult().collectLatest { networks.emit(it) }
		}
	}

	fun onNetworkSelected(network: Network) = viewModelScope.launch { selectedNetwork.emit(network) }
}
