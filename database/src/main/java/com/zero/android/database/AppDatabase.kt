package com.zero.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zero.android.database.converter.DateConverters
import com.zero.android.database.converter.ListConverters
import com.zero.android.database.dao.NetworkDao
import com.zero.android.database.dao.ProfileDao
import com.zero.android.database.dao.UserDao
import com.zero.android.database.model.NetworkEntity
import com.zero.android.database.model.ProfileEntity
import com.zero.android.database.model.UserEntity

@Database(
    entities = [UserEntity::class, ProfileEntity::class, NetworkEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverters::class, ListConverters::class)
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

    abstract fun profileDao(): ProfileDao

    abstract fun networkDao(): NetworkDao
}
