package com.zero.android.data

import com.zero.android.data.manager.ConnectionManager
import com.zero.android.data.manager.ConnectionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

	@Binds fun bindUserRepository(connectionManager: ConnectionManagerImpl): ConnectionManager
}
