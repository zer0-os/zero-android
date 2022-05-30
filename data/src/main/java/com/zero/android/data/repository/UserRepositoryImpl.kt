package com.zero.android.data.repository

import com.zero.android.network.service.UserService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(userService: UserService) : UserRepository
