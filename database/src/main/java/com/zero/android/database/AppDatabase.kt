package com.zero.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zero.android.database.converter.DateConverters
import com.zero.android.database.converter.ListConverters
import com.zero.android.database.dao.DirectChannelDaoInterface
import com.zero.android.database.dao.GroupChannelDaoInterface
import com.zero.android.database.dao.MemberDao
import com.zero.android.database.dao.MessageDaoInterface
import com.zero.android.database.dao.NetworkDao
import com.zero.android.database.dao.ProfileDao
import com.zero.android.database.dao.UserDao
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.ChannelMembersCrossRef
import com.zero.android.database.model.ChannelOperatorsCrossRef
import com.zero.android.database.model.MemberEntity
import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageMentionCrossRef
import com.zero.android.database.model.MessageWithRefs
import com.zero.android.database.model.NetworkEntity
import com.zero.android.database.model.ProfileEntity
import com.zero.android.database.model.UserEntity

@Database(
	entities =
	[
		UserEntity::class,
		ProfileEntity::class,
		NetworkEntity::class,
		MessageEntity::class,
		MemberEntity::class,
		ChannelEntity::class,
		MessageMentionCrossRef::class,
		ChannelMembersCrossRef::class,
		ChannelOperatorsCrossRef::class
	],
	views = [MessageWithRefs::class],
	version = 1,
	exportSchema = false
)
@TypeConverters(DateConverters::class, ListConverters::class)
abstract class AppDatabase : RoomDatabase() {

	companion object {

		private const val DATABASE_NAME = "zero-database"

		@Volatile private var INSTANCE: AppDatabase? = null

		fun getInstance(context: Context): AppDatabase =
			INSTANCE ?: synchronized(this) { INSTANCE ?: buildDatabase(context).also { INSTANCE = it } }

		private fun buildDatabase(context: Context) =
			Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
				.fallbackToDestructiveMigration()
				.build()
	}

	abstract fun userDao(): UserDao

	abstract fun profileDao(): ProfileDao

	abstract fun memberDao(): MemberDao

	abstract fun networkDao(): NetworkDao

	abstract fun directChannelDao(): DirectChannelDaoInterface

	abstract fun groupChannelDao(): GroupChannelDaoInterface

	abstract fun messageDao(): MessageDaoInterface
}
