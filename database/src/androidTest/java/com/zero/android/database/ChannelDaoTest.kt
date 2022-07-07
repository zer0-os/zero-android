package com.zero.android.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.util.FakeData
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ChannelDaoTest : BaseDatabaseTest() {

	private val directChannel = FakeData.DirectChannelWithRefs(id = "directChannelId")
	private val groupChannel =
		FakeData.GroupChannelWithRefs(id = "groupChannelId", networkId = "networkId")

	@Before fun setup() = runTest { db.networkDao().insert(FakeData.NetworkEntity(id = "networkId")) }

	@Test
	fun insertDirectChannel() = runTest {
		channelDao.upsert(directChannel)
		channelDao.upsert(directChannel) // Checking 2nd insert

		val data = channelDao.getDirectChannel(directChannel.channel.id).first()
		assertEquals(directChannel.channel.id, data.channel.id)
		assertEquals(directChannel.lastMessage?.message?.id, data.lastMessage?.message?.id)
		assertEquals(directChannel.channel.id, data.lastMessage?.message?.channelId)
		assertEquals(directChannel.members.size, data.members.size)
	}

	@Test
	fun insertGroupChannel() = runTest {
		channelDao.upsert(groupChannel)
		channelDao.upsert(groupChannel) // Checking 2nd insert

		val data = channelDao.getGroupChannel(groupChannel.channel.id).first()
		assertEquals(groupChannel.channel.id, data.channel.id)
		assertEquals(groupChannel.lastMessage?.message?.id, data.lastMessage?.message?.id)
		assertEquals(groupChannel.lastMessage?.author?.id, data.lastMessage?.author?.id)
		assertEquals(groupChannel.channel.id, data.lastMessage?.message?.channelId)
		assertEquals(groupChannel.members.size, data.members.size)
		assertEquals(groupChannel.operators.size, data.operators.size)
	}

	@Test
	fun deleteDirectChannel() = runTest {
		channelDao.upsert(directChannel)
		channelDao.delete(directChannel.channel)

		assertNull(channelDao.getDirectChannel(directChannel.channel.id).firstOrNull())
		assertNull(messageDao.get(directChannel.lastMessage?.message?.id!!).firstOrNull())
	}

	@Test
	fun deleteGroupChannel() = runTest {
		channelDao.upsert(groupChannel)
		channelDao.delete(groupChannel.channel)

		assertNull(channelDao.getDirectChannel(groupChannel.channel.id).firstOrNull())
		assertNull(messageDao.get(groupChannel.lastMessage?.message?.id!!).firstOrNull())
	}
}
