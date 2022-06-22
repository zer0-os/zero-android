package com.zero.android.network.service

import com.zero.android.models.Channel
import com.zero.android.models.ChannelCategory
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiDirectChannel
import com.zero.android.network.model.ApiGroupChannel
import kotlinx.coroutines.flow.Flow

interface ChannelService {

    suspend fun getCategories(networkId: String): Flow<List<ChannelCategory>>

    suspend fun getGroupChannels(
        networkId: String,
        type: ChannelType = ChannelType.GROUP
    ): Flow<List<ApiGroupChannel>>

    suspend fun getDirectChannels(): Flow<List<ApiDirectChannel>>

    suspend fun createChannel(networkId: String, channel: Channel): Flow<ApiChannel>

    suspend fun getChannel(url: String, type: ChannelType = ChannelType.GROUP): Flow<ApiChannel>

    suspend fun joinChannel(channel: Channel)

    suspend fun deleteChannel(channel: Channel)
}
