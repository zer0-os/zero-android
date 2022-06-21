package com.zero.android.data.repository

import com.zero.android.models.Channel
import com.zero.android.models.ChannelCategory
import com.zero.android.models.DirectChannel
import com.zero.android.models.GroupChannel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

	suspend fun getCategories(networkId: String): Flow<List<ChannelCategory>>

	suspend fun getDirectChannels(): Flow<List<DirectChannel>>

	suspend fun getGroupChannels(networkId: String): Flow<List<GroupChannel>>

	suspend fun getGroupChannel(id: String): Flow<GroupChannel>

	suspend fun getDirectChannel(id: String): Flow<GroupChannel>

	suspend fun joinChannel(channel: Channel)

	suspend fun deleteChannel(channel: Channel)
}
