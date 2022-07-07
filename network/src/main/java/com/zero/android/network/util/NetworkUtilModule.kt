package com.zero.android.network.util

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkUtilModule {

	@Singleton
	@Provides
	fun provideNetworkMediaUtil(@ApplicationContext context: Context) = NetworkMediaUtil(context)
}
