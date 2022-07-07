package com.zero.android.data.repository

import com.zero.android.data.conversion.toEntity
import com.zero.android.data.conversion.toModel
import com.zero.android.database.dao.NetworkDao
import com.zero.android.database.model.toModel
import com.zero.android.datastore.AppPreferences
import com.zero.android.network.service.ChannelCategoryService
import com.zero.android.network.service.NetworkService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class NetworkRepositoryImpl
@Inject
constructor(
	private val networkDao: NetworkDao,
	private val preferences: AppPreferences,
	private val networkService: NetworkService,
	private val categoryService: ChannelCategoryService
) : NetworkRepository {

	override suspend fun getNetworks() = flow {
		networkDao.getAll().firstOrNull()?.let { networks -> emit(networks.map { it.toModel() }) }

		networkService.getNetworks(preferences.userId()).let { networks ->
			networkDao.upsert(networks.map { it.toEntity() })
			emit(networks.map { it.toModel() })
		}
	}

	override suspend fun getCategories(networkId: String) = flow {
		networkDao.getCategories(networkId).mapNotNull { emit(it) }
		categoryService.getCategories(networkId).firstOrNull()?.let { emit(it) }
	}
}
