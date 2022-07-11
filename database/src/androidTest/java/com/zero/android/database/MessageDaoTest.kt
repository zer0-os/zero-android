package com.zero.android.database

import android.database.sqlite.SQLiteConstraintException
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.model.fake.FakeEntity
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MessageDaoTest : BaseDatabaseTest() {

	private val message = FakeEntity.MessageWithRefs(channelId = "channelId")

	@Before
	fun setup() = runTest {
		db.networkDao().insert(FakeEntity.NetworkEntity())
		channelDao.upsert(FakeEntity.DirectChannelWithRefs(id = "channelId", lastMessage = null))
	}

	@Test
	fun insertMessage() = runTest {
		messageDao.upsert(message)
		messageDao.upsert(message) // Checking 2nd insert

		val data = messageDao.get(message.message.id).first()
		assertEquals(message.message.id, data?.message?.id)
		assertEquals(message.parentMessage?.id, data?.parentMessage?.id)
		assertEquals(message.parentMessageAuthor?.id, data?.parentMessageAuthor?.id)
		assertEquals(message.author.id, data?.author?.id)
		assertEquals(message.mentions?.size, data?.mentions?.size)
	}

	@Test
	fun updateMessage() = runTest {
		messageDao.upsert(message)
		messageDao.upsert(FakeEntity.MessageWithRefs(channelId = "channelId", authorId = "memberTwo"))

		val data = messageDao.get(message.message.id).first()
		assertEquals("channelId", data?.message?.channelId)
		assertEquals("memberTwo", data?.author?.id)
	}

	@Test
	fun deleteConstraints() = runTest {
		messageDao.upsert(message)

		try {
			db.memberDao().delete(message.author)
			db.memberDao().delete(message.parentMessageAuthor!!)
			db.memberDao().delete(message.mentions!![0])
			messageDao.delete(message.parentMessage!!)
		} catch (_: SQLiteConstraintException) {}

		val data = messageDao.get(message.message.id).first()
		assertEquals(message.parentMessage?.id, data?.parentMessage?.id)
		assertEquals(message.parentMessageAuthor?.id, data?.parentMessageAuthor?.id)
		assertEquals(message.author.id, data?.author?.id)
		assertEquals(message.mentions?.size, data?.mentions?.size)
	}

	@Test
	fun deleteMessage() = runTest {
		messageDao.upsert(message)
		messageDao.delete(message.message)

		assertNull(messageDao.get(message.message.id).firstOrNull())
		assertNotNull(db.memberDao().get(message.author.id).first())
		assertNotNull(db.memberDao().get(message.parentMessageAuthor!!.id).firstOrNull())
		assertNotNull(messageDao.get(message.parentMessage!!.id).firstOrNull())
		assertNotNull(db.memberDao().get(message.mentions!![0].id).firstOrNull())
		assertNotNull(db.memberDao().get(message.mentions!![1].id).firstOrNull())
	}
}
