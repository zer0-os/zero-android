package com.zero.android.network

import com.zero.android.common.system.Logger
import com.zero.android.network.chat.sendbird.SendBirdChannelService
import com.zero.android.network.chat.sendbird.SendBirdChatService
import com.zero.android.network.service.AccessService
import com.zero.android.network.service.AccountService
import com.zero.android.network.service.ChannelService
import com.zero.android.network.service.ChatService
import com.zero.android.network.service.NetworkService
import com.zero.android.network.service.ProfileService
import com.zero.android.network.service.UserService
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

	@Singleton
	@Provides
	fun provideAccessService(retrofit: Retrofit) = retrofit.base.create(AccessService::class.java)

	@Singleton
	@Provides
	fun provideAccountService(retrofit: Retrofit) = retrofit.api.create(AccountService::class.java)

	@Singleton
	@Provides
	fun provideProfileService(retrofit: Retrofit) = retrofit.api.create(ProfileService::class.java)

	@Singleton
	@Provides
	fun provideNetworkService(retrofit: Retrofit) = retrofit.api.create(NetworkService::class.java)

	@Singleton
	@Provides
	fun provideChannelService(logger: Logger): ChannelService = SendBirdChannelService(logger)

	@Singleton
	@Provides
	fun provideMessageService(logger: Logger): ChatService = SendBirdChatService(logger)
}
