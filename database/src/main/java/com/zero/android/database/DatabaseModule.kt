package com.zero.android.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

	@Provides
	@Singleton
	fun provideDatabaseCleaner(appDatabase: AppDatabase) = DatabaseCleaner(appDatabase)
}
