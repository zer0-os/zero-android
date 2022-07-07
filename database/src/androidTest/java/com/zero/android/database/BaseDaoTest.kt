package com.zero.android.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.util.FakeData
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class BaseDaoTest : BaseDatabaseTest() {

	@Test
	fun insert() = runTest {
		db.networkDao().insert(FakeData.NetworkEntity("one"))
		db.networkDao().getAll().firstOrNull().let { data ->
			assertNotNull(data)
			assertEquals("one", data?.get(0)?.id)
		}
	}

	@Test
	fun update() = runTest {
		db.networkDao().insert(FakeData.NetworkEntity("one", name = "old name"))
		db.networkDao().get("one").first().let { assertEquals("old name", it.name) }

		db.networkDao().update(FakeData.NetworkEntity("one", name = "new name"))
		db.networkDao().get("one").first().let { assertEquals("new name", it.name) }
	}

	@Test
	fun upsertSingle() = runTest {
		db.networkDao().insert(FakeData.NetworkEntity("one", name = "old name"))
		db.networkDao().get("one").first().let { assertEquals("old name", it.name) }

		db.networkDao().upsert(FakeData.NetworkEntity("one", name = "new name"))
		db.networkDao().get("one").first().let { assertEquals("new name", it.name) }
	}

	@Test
	fun upsertList() = runTest {
		var networks =
			mutableListOf(
				FakeData.NetworkEntity("one", name = "old name"),
				FakeData.NetworkEntity("two", name = "old name")
			)

		db.networkDao().insert(networks)

		db.networkDao().getAll().firstOrNull().let { data ->
			assertNotNull(data)
			assertEquals(2, data?.size)
		}

		networks =
			mutableListOf(
				FakeData.NetworkEntity("one", name = "updated"),
				FakeData.NetworkEntity("two", name = "updated"),
				FakeData.NetworkEntity("three"),
				FakeData.NetworkEntity("four")
			)

		db.networkDao().upsert(networks)

		db.networkDao().getAll().firstOrNull().let { data ->
			assertNotNull(data)
			assertEquals(4, data?.size)

			data?.find { it.id == "one" }?.let { assertEquals("Updated name", "updated", it.name) }
		}
	}

	@Test
	fun delete() = runTest {
		db.networkDao().insert(FakeData.NetworkEntity("one"))
		assertNotNull(db.networkDao().get("one").firstOrNull())
		db.networkDao().delete(FakeData.NetworkEntity("one"))
		assertNull(db.networkDao().get("one").firstOrNull())
	}
}
