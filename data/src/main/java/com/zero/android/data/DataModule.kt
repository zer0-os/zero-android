package com.zero.android.data

import com.zero.android.data.repository.UserRepository
import com.zero.android.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

	@Binds fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}
