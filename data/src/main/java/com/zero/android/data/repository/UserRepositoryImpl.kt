package com.zero.android.data.repository

import com.zero.android.data.conversion.toEntity
import com.zero.android.data.conversion.toModel
import com.zero.android.database.AppCleaner
import com.zero.android.database.AppPreferences
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
	private val appCleaner: AppCleaner
) : UserRepository {

	override suspend fun login(credentials: AuthCredentials) {
		preferences.setAuthCredentials(credentials)
		getUser().last()
	}

	override suspend fun logout() = appCleaner.clean()

	override suspend fun getUser() = flow {
		userDao.getAll().firstOrNull()?.firstOrNull()?.let { cachedUser -> emit(cachedUser.toModel()) }

		userService.getUser().data.let { user ->
			userDao.insert(user.toEntity())
			profileDao.insert(user.profile.toEntity())
			emit(user.toModel())
		}
	}
}
