package com.zero.android.network.chat

import com.zero.android.common.system.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

	@Singleton
	@Provides
	fun provideChatProvider(logger: Logger): ChatProvider = SendBirdProvider(logger)
}
