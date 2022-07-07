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
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NetworkDaoTest : BaseDatabaseTest() {

	private val network = FakeData.NetworkEntity()

	@Test
	fun insertNetwork() = runTest {
		db.networkDao().insert(network)

		val data = db.networkDao().getById(network.id).first()
		assertEquals(network.id, data.id)
	}

	@Test
	fun deleteNetwork() = runTest {
		db.networkDao().insert(network)
		db.networkDao().delete(network)

		val data = db.networkDao().getById(network.id).firstOrNull()
		assertNull(data)
	}

	@Test
	fun getCategories() = runTest {
		db.networkDao().insert(network)
		channelDao.upsert(
			FakeData.GroupChannelWithRefs(
				id = "groupOneId",
				networkId = network.id,
				category = "one",
				lastMessage = null
			),
			FakeData.GroupChannelWithRefs(
				id = "groupTwoId",
				networkId = network.id,
				category = "two",
				lastMessage = null
			),
			FakeData.GroupChannelWithRefs(
				id = "groupThreeId",
				networkId = network.id,
				category = "one",
				lastMessage = null
			)
		)

		val categories = db.networkDao().getCategories(network.id).first()
		assertEquals(2, categories.size)
	}
}
