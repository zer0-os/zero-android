package com.zero.android.database

import com.zero.android.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

	@Provides fun providesUserDao(database: AppDatabase): UserDao = database.userDao()
}
