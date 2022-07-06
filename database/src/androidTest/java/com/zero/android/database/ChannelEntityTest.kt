package com.zero.android.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.DirectChannelWithRefs
import com.zero.android.database.model.GroupChannelWithRefs
import com.zero.android.database.model.MemberEntity
import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageWithRefs
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ChannelEntityTest : BaseDatabaseTest() {

	private val directChannel =
		DirectChannelWithRefs(
			channel =
			ChannelEntity(
				id = "directChannelId",
				lastMessageId = "directLastMessageId",
				isDirectChannel = true,
				memberCount = 2
			),
			members = listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberTwo")),
			lastMessage =
			MessageWithRefs(
				message =
				MessageEntity(
					id = "directLastMessageId",
					authorId = "authorId",
					channelId = "directChannelId",
					type = MessageType.TEXT,
					status = MessageStatus.PENDING
				),
				author = MemberEntity(id = "authorId"),
				mentions =
				listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberTwo"))
			)
		)

	private val groupChannel =
		GroupChannelWithRefs(
			createdBy = MemberEntity(id = "memberFive"),
			channel =
			ChannelEntity(
				id = "groupChannelId",
				lastMessageId = "groupLastMessageId",
				isDirectChannel = false,
				memberCount = 2
			),
			members = listOf(MemberEntity(id = "memberFive"), MemberEntity(id = "memberFour")),
			operators = listOf(MemberEntity(id = "memberFive")),
			lastMessage =
			MessageWithRefs(
				message =
				MessageEntity(
					id = "groupLastMessageId",
					authorId = "authorId",
					channelId = "groupChannelId",
					type = MessageType.TEXT,
					status = MessageStatus.PENDING
				),
				author = MemberEntity(id = "authorId"),
				mentions =
				listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberThree"))
			)
		)

	@Test
	fun insertDirectChannel() = runTest {
		channelDao.insert(directChannel)

		val data = channelDao.getDirectChannelById(directChannel.channel.id).first()
		assertEquals(directChannel.channel.id, data.channel.id)
		assertEquals(directChannel.lastMessage?.message?.id, data.lastMessage?.message?.id)
		assertEquals(directChannel.channel.id, data.lastMessage?.message?.channelId)
		assertEquals(directChannel.members.size, data.members.size)
	}

	@Test
	fun insertGroupChannel() = runTest {
		channelDao.insert(groupChannel)

		val data = channelDao.getGroupChannelById(groupChannel.channel.id).first()
		assertEquals(groupChannel.channel.id, data.channel.id)
		assertEquals(groupChannel.lastMessage?.message?.id, data.lastMessage?.message?.id)
		assertEquals(groupChannel.lastMessage?.author?.id, data.lastMessage?.author?.id)
		assertEquals(groupChannel.channel.id, data.lastMessage?.message?.channelId)
		assertEquals(groupChannel.members.size, data.members.size)
		assertEquals(groupChannel.operators.size, data.operators.size)
	}

	@Test
	fun deleteDirectChannel() = runTest {
		channelDao.insert(directChannel)
		channelDao.delete(directChannel.channel)

		assertNull(channelDao.getDirectChannelById(directChannel.channel.id).firstOrNull())
		assertNull(messageDao.getById(directChannel.lastMessage?.message?.id!!).firstOrNull())
	}

	@Test
	fun deleteGroupChannel() = runTest {
		channelDao.insert(groupChannel)
		channelDao.delete(groupChannel.channel)

		assertNull(channelDao.getDirectChannelById(groupChannel.channel.id).firstOrNull())
		assertNull(messageDao.getById(groupChannel.lastMessage?.message?.id!!).firstOrNull())
	}
}
