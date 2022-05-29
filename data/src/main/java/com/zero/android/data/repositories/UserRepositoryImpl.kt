package com.zero.android.data.repositories

import com.zero.android.network.services.UserService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(userService: UserService) : UserRepository
