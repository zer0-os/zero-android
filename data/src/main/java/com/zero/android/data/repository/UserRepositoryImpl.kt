package com.zero.android.data.repository

import com.zero.android.data.conversion.toEntity
import com.zero.android.database.AppPreferences
import com.zero.android.database.dao.UserDao
import com.zero.android.models.AuthCredentials
import com.zero.android.network.service.UserService
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
	private val userDao: UserDao,
	private val preferences: AppPreferences,
	private val userService: UserService
) : UserRepository {

	override suspend fun login(credentials: AuthCredentials) {
		preferences.setAuthCredentials(credentials)
	}

	override suspend fun logout() {
		TODO("Not yet implemented")
	}

	override suspend fun getUser() {
		val user = userService.getUser().data
		userDao.insert(user.toEntity())
	}
}
