package com.zero.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zero.android.database.dao.UserDao

@Database(entities = [], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

	companion object {

		@Volatile private var INSTANCE: AppDatabase? = null

		fun getInstance(context: Context): AppDatabase =
			INSTANCE ?: synchronized(this) { INSTANCE ?: buildDatabase(context).also { INSTANCE = it } }

		private fun buildDatabase(context: Context) =
			Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "zero-database")
				.fallbackToDestructiveMigration()
				.build()
	}

	abstract fun userDao(): UserDao
}
