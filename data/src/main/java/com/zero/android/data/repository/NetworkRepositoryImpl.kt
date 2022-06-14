package com.zero.android.data.repository

import com.zero.android.data.conversion.toEntity
import com.zero.android.data.conversion.toModel
import com.zero.android.database.AppPreferences
import com.zero.android.database.dao.NetworkDao
import com.zero.android.database.model.toModel
import com.zero.android.network.service.NetworkService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepositoryImpl
@Inject
constructor(
	private val networkDao: NetworkDao,
	private val preferences: AppPreferences,
	private val networkService: NetworkService
) : NetworkRepository {

	override suspend fun getNetworks() = flow {
		networkDao.getAll().firstOrNull()?.let { networks -> emit(networks.map { it.toModel() }) }

		networkService.getNetworks(preferences.userId()).let { networks ->
			networkDao.insert(*networks.map { it.toEntity() }.toTypedArray())
			emit(networks.map { it.toModel() })
		}
	}
}
