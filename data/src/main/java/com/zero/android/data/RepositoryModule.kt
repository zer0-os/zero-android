package com.zero.android.data

import com.zero.android.data.repository.ChannelRepository
import com.zero.android.data.repository.ChannelRepositoryImpl
import com.zero.android.data.repository.ChatRepository
import com.zero.android.data.repository.ChatRepositoryImpl
import com.zero.android.data.repository.NetworkRepository
import com.zero.android.data.repository.NetworkRepositoryImpl
import com.zero.android.data.repository.UserRepository
import com.zero.android.data.repository.UserRepositoryImpl
import com.zero.android.data.repository.mediaplayer.MediaPlayerRepository
import com.zero.android.data.repository.mediaplayer.MediaPlayerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

	@Binds fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

	@Binds fun bindNetworkRepository(networkRepository: NetworkRepositoryImpl): NetworkRepository

	@Binds fun bindChannelRepository(channelRepository: ChannelRepositoryImpl): ChannelRepository

	@Binds fun bindChatRepository(chatRepository: ChatRepositoryImpl): ChatRepository

    @Binds fun bindMediaPlayerRepository(mediaPlayerRepository: MediaPlayerRepositoryImpl): MediaPlayerRepository
}
