package com.zero.android.database

import android.database.sqlite.SQLiteConstraintException
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.DirectChannelWithRefs
import com.zero.android.database.model.MemberEntity
import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageWithRefs
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
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
class MessageEntityTest : BaseDatabaseTest() {

	private val message =
		MessageWithRefs(
			message =
			MessageEntity(
				id = "messageId",
				authorId = "authorId",
				parentMessageId = "parentMessageId",
				parentMessageAuthorId = "parentAuthorId",
				channelId = "channelId",
				type = MessageType.TEXT,
				status = MessageStatus.PENDING
			),
			author = MemberEntity(id = "authorId"),
			parentMessage =
			MessageEntity(
				id = "parentMessageId",
				authorId = "parentAuthorId",
				channelId = "channelId",
				type = MessageType.TEXT,
				status = MessageStatus.PENDING
			),
			parentMessageAuthor = MemberEntity(id = "parentAuthorId"),
			mentions = listOf(MemberEntity(id = "mentionOneId"), MemberEntity(id = "mentionTwoId"))
		)

	@Before
	fun setup() = runTest {
		channelDao.insert(
			DirectChannelWithRefs(
				channel = ChannelEntity("channelId", isDirectChannel = false),
				members = emptyList()
			)
		)
	}

	@Test
	fun insertMessage() = runTest {
		messageDao.insert(message)

		val data = messageDao.getById(message.message.id).first()
		assertEquals(message.message.id, data.message.id)
		assertEquals(message.parentMessage?.id, data.parentMessage?.id)
		assertEquals(message.parentMessageAuthor?.id, data.parentMessageAuthor?.id)
		assertEquals(message.author.id, data.author.id)
		assertEquals(message.mentions?.size, data.mentions?.size)
	}

	@Test
	fun deleteConstraints() = runTest {
		messageDao.insert(message)

		try {
			db.memberDao().delete(message.author)
			db.memberDao().delete(message.parentMessageAuthor!!)
			db.memberDao().delete(message.mentions!![0])
			messageDao.delete(message.parentMessage!!)
		} catch (_: SQLiteConstraintException) {}

		val data = messageDao.getById(message.message.id).first()
		assertEquals(message.parentMessage?.id, data.parentMessage?.id)
		assertEquals(message.parentMessageAuthor?.id, data.parentMessageAuthor?.id)
		assertEquals(message.author.id, data.author.id)
		assertEquals(message.mentions?.size, data.mentions?.size)
	}

	@Test
	fun deleteMessage() = runTest {
		messageDao.insert(message)
		messageDao.delete(message.message)

		assertNull(messageDao.getById(message.message.id).firstOrNull())
		assertNotNull(db.memberDao().getById(message.author.id).first())
		assertNotNull(db.memberDao().getById(message.parentMessageAuthor!!.id).firstOrNull())
		assertNotNull(messageDao.getById(message.parentMessage!!.id).firstOrNull())
		assertNotNull(db.memberDao().getById(message.mentions!![0].id).firstOrNull())
		assertNotNull(db.memberDao().getById(message.mentions!![1].id).firstOrNull())
	}
}
