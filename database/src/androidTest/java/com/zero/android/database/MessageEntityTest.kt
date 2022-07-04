package com.zero.android.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.DirectChannelWithRefs
import com.zero.android.database.model.MemberEntity
import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageWithRefs
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MessageEntityTest : BaseDatabaseTest() {

	@Test
	fun messages() = runTest {
		val message =
			MessageWithRefs(
				message =
				MessageEntity(
					id = "messageId",
					channelId = "channelId",
					type = MessageType.TEXT,
					status = MessageStatus.PENDING
				),
				author = MemberEntity(id = "authorId"),
				parentMessage =
				MessageEntity(
					id = "parentMessage",
					channelId = "channelId",
					type = MessageType.TEXT,
					status = MessageStatus.PENDING
				),
				parentMessageAuthor = MemberEntity(id = "parentAuthorId"),
				mentions = listOf(MemberEntity(id = "mentionOne"), MemberEntity(id = "mentionTwo"))
			)

		channelDao.insert(
			DirectChannelWithRefs(
				channel = ChannelEntity("channelId", isDirectChannel = false),
				members = emptyList()
			)
		)

		messageDao.insert(message)

		val data = messageDao.getById("messageId").first()
		assertEquals(message.message.id, data.message.id)
		assertEquals(message.parentMessage?.id, data.parentMessage?.id)
		assertEquals(message.author.id, data.author.id)
		assertEquals(message.mentions?.size, data.mentions?.size)
	}
}
