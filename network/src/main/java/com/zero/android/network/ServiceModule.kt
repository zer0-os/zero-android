package com.zero.android.network

import com.zero.android.network.services.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

	@Singleton
	@Provides
	fun provideUserService(retrofit: Retrofit) = retrofit.api.create(UserService::class.java)
}
