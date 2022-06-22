package com.zero.android.feature.channels.ui.directchannels

import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.data.repository.ChannelRepository
import com.zero.android.database.AppPreferences
import com.zero.android.models.DirectChannel
import com.zero.android.models.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DirectChannelsViewModel
@Inject
constructor(
    private val preferences: AppPreferences,
    private val channelRepository: ChannelRepository
) : BaseViewModel() {

    private lateinit var network: Network
    val loggedInUserId
        get() = runBlocking(Dispatchers.IO) { preferences.userId() }
    val channels = MutableStateFlow<Result<List<DirectChannel>>>(Result.Loading)

    fun onNetworkUpdated(network: Network) {
        this.network = network
        loadChannels()
    }

    private fun loadChannels() {
        ioScope.launch {
            channelRepository.getDirectChannels().asResult().collectLatest { channels.emit(it) }
        }
    }
}
