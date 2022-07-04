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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ChannelEntityTest : BaseDatabaseTest() {

	@Test
	fun directChannel() = runTest {
		val members = listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberTwo"))
		val channel =
			ChannelEntity(id = "directChannelId", isDirectChannel = true, memberCount = members.size)

		val group =
			DirectChannelWithRefs(
				channel = channel,
				members = members,
				lastMessage =
				MessageWithRefs(
					message =
					MessageEntity(
						id = "lastMessageId",
						channelId = channel.id,
						type = MessageType.TEXT,
						status = MessageStatus.PENDING
					),
					author = MemberEntity(id = "authorId"),
					mentions =
					listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberTwo"))
				)
			)

		channelDao.insert(group)

		val data = channelDao.getDirectChannelById("directChannelId").first()
		assertEquals(group.channel.id, data.channel.id)
		assertEquals(group.lastMessage?.message?.id, data.lastMessage?.message?.id)
		assertEquals(group.channel.id, data.lastMessage?.message?.channelId)
		assertEquals(group.members.size, data.members.size)
	}

	@Test
	fun groupChannel() = runTest {
		val members = listOf(MemberEntity(id = "memberFive"), MemberEntity(id = "memberFour"))
		val channel =
			ChannelEntity(id = "groupChannelId", isDirectChannel = false, memberCount = members.size)

		val group =
			GroupChannelWithRefs(
				createdBy = MemberEntity(id = "memberFive"),
				channel = channel,
				members = members,
				operators = listOf(MemberEntity(id = "memberFive")),
				lastMessage =
				MessageWithRefs(
					message =
					MessageEntity(
						id = "messageId2",
						channelId = channel.id,
						type = MessageType.TEXT,
						status = MessageStatus.PENDING
					),
					author = MemberEntity(id = "authorId"),
					mentions =
					listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberThree"))
				)
			)

		channelDao.insert(group)

		val data = channelDao.getGroupChannelById("groupChannelId").first()
		assertEquals(group.channel.id, data.channel.id)
		assertEquals(group.lastMessage?.message?.id, data.lastMessage?.message?.id)
		assertEquals(group.lastMessage?.author?.id, data.lastMessage?.author?.id)
		assertEquals(group.channel.id, data.lastMessage?.message?.channelId)
		assertEquals(group.members.size, data.members.size)
		assertEquals(group.operators.size, data.operators.size)
	}
}
