package com.zero.android.network.service

import com.zero.android.models.Channel
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.model.ApiChannel
import kotlinx.coroutines.flow.Flow

interface ChannelService {

	suspend fun getChannels(
		networkId: String,
		type: ChannelType = ChannelType.GROUP
	): Flow<List<ApiChannel>>

	suspend fun createChannel(networkId: String, channel: Channel): Flow<ApiChannel>

	suspend fun getChannel(url: String, type: ChannelType = ChannelType.GROUP): Flow<ApiChannel>

	suspend fun joinChannel(channel: Channel)

	suspend fun deleteChannel(channel: Channel)
}
