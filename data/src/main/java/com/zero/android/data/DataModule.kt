package com.zero.android.data

import com.zero.android.data.delegates.Preferences
import com.zero.android.data.delegates.PreferencesImpl
import com.zero.android.data.manager.ConnectionManager
import com.zero.android.data.manager.ConnectionManagerImpl
import com.zero.android.data.manager.DataCleaner
import com.zero.android.data.manager.DataCleanerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

	@Binds fun bindConnectionManager(connectionManager: ConnectionManagerImpl): ConnectionManager

	@Binds fun provideDataCleaner(dataCleaner: DataCleanerImpl): DataCleaner

	@Binds fun providePreferences(preferences: PreferencesImpl): Preferences
}
