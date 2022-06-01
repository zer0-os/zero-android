package com.zero.android.data.repository

import com.zero.android.models.AuthCredentials
import com.zero.android.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

	suspend fun login(credentials: AuthCredentials)

	suspend fun logout()

	suspend fun getUser(): Flow<User>
}
