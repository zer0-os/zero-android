package com.zero.android.network.service

import com.zero.android.models.ChannelCategory
import kotlinx.coroutines.flow.Flow

interface ChannelCategoryService {

	suspend fun getCategories(networkId: String): Flow<List<ChannelCategory>>
}
