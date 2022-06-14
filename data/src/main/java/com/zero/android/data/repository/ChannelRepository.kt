package com.zero.android.data.repository

import com.zero.android.models.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

	suspend fun getOpenChannels(networkId: String): Flow<List<Channel>>

	suspend fun getGroupChannels(networkId: String): Flow<List<Channel>>
}
