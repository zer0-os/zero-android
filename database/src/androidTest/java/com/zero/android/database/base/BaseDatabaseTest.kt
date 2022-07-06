package com.zero.android.database.base

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.AppDatabase
import com.zero.android.database.dao.ChannelDao
import com.zero.android.database.dao.MessageDao
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class BaseDatabaseTest {

	protected lateinit var db: AppDatabase

	protected lateinit var channelDao: ChannelDao
	protected lateinit var messageDao: MessageDao

	@Before
	fun onCreateDB() {
		val context = ApplicationProvider.getApplicationContext<Context>()
		db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

		messageDao = MessageDao(db.messageDao(), db.memberDao())
		channelDao = ChannelDao(db.directChannelDao(), db.groupChannelDao(), db.memberDao(), messageDao)
	}

	@After
	@Throws(IOException::class)
	open fun onCloseDB() {
		db.close()
	}
}
