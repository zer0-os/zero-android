package com.zero.android.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

	@Provides
	@Singleton
	fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
		return PreferenceDataStoreFactory.create(
			corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
			migrations = listOf(),
			scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
			produceFile = { context.preferencesDataStoreFile("app_preferences") }
		)
	}

	@Provides
	@Singleton
	fun provideDatastoreCleaner(dataStore: DataStore<Preferences>) = DatastoreCleaner(dataStore)

	@Provides
	@Singleton
	fun provideAppPreferences(dataStore: DataStore<Preferences>) = AppPreferences(dataStore)

	@Provides
	@Singleton
	fun provideChatPreferences(dataStore: DataStore<Preferences>) = ChatPreferences(dataStore)
}
