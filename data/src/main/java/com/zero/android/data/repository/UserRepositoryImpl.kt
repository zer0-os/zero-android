package com.zero.android.data.repository

import com.zero.android.common.system.Logger
import com.zero.android.data.conversion.toEntity
import com.zero.android.data.conversion.toModel
import com.zero.android.data.manager.ConnectionManager
import com.zero.android.database.AppPreferences
import com.zero.android.database.DataCleaner
import com.zero.android.database.dao.ProfileDao
import com.zero.android.database.dao.UserDao
import com.zero.android.database.model.toModel
import com.zero.android.models.AuthCredentials
import com.zero.android.network.service.UserService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
	private val userDao: UserDao,
	private val profileDao: ProfileDao,
	private val preferences: AppPreferences,
	private val userService: UserService,
	private val connectionManager: ConnectionManager,
	private val dataCleaner: DataCleaner,
	private val logger: Logger
) : UserRepository {

	override suspend fun login(credentials: AuthCredentials) {
		preferences.setAuthCredentials(credentials)
		try {
			val user = getUser().last()

			preferences.setUserId(user.id)
			preferences.setChatToken(getChatAccessToken(getChatAccessToken(credentials.accessToken)))

			connectionManager.connect()
		} catch (e: Exception) {
			logger.e(e)
			dataCleaner.clean()
			throw e
		}
	}

	override suspend fun logout() {
		dataCleaner.clean()
		connectionManager.disconnect()
	}

	private suspend fun getChatAccessToken(accessToken: String): String {
		val chatAccess = userService.getChatAccessToken(hashMapOf("idToken" to accessToken))
		return chatAccess.chatAccessToken
	}

	override suspend fun getUser() = flow {
		userDao.getAll().firstOrNull()?.firstOrNull()?.let { cachedUser -> emit(cachedUser.toModel()) }

		userService.getUser().let { user ->
			userDao.insert(user.toEntity())
			profileDao.insert(user.profile.toEntity(user.id))
			emit(user.toModel())
		}
	}
}
