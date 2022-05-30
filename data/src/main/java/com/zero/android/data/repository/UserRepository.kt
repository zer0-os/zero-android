package com.zero.android.data.repository

import com.zero.android.models.AuthCredentials

interface UserRepository {

	suspend fun login(credentials: AuthCredentials)

	suspend fun logout()

	suspend fun getUser()
}
