package com.zero.android.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.model.fake.FakeEntity
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
		db.networkDao().insert(FakeEntity.NetworkEntity("one"))
		db.networkDao().getAll().firstOrNull().let { data ->
			assertNotNull(data)
			assertEquals("one", data?.get(0)?.id)
		}
	}

	@Test
	fun update() = runTest {
		db.networkDao().insert(FakeEntity.NetworkEntity("one", name = "old name"))
		db.networkDao().get("one").first().let { assertEquals("old name", it?.name) }

		db.networkDao().update(FakeEntity.NetworkEntity("one", name = "new name"))
		db.networkDao().get("one").first().let { assertEquals("new name", it?.name) }
	}

	@Test
	fun upsertSingle() = runTest {
		db.networkDao().insert(FakeEntity.NetworkEntity("one", name = "old name"))
		db.networkDao().get("one").first().let { assertEquals("old name", it?.name) }

		db.networkDao().upsert(FakeEntity.NetworkEntity("one", name = "new name"))
		db.networkDao().get("one").first().let { assertEquals("new name", it?.name) }
	}

	@Test
	fun upsertList() = runTest {
		var networks =
			mutableListOf(
				FakeEntity.NetworkEntity("one", name = "old name"),
				FakeEntity.NetworkEntity("two", name = "old name")
			)

		db.networkDao().insert(networks)

		db.networkDao().getAll().firstOrNull().let { data ->
			assertNotNull(data)
			assertEquals(2, data?.size)
		}

		networks =
			mutableListOf(
				FakeEntity.NetworkEntity("one", name = "updated"),
				FakeEntity.NetworkEntity("two", name = "updated"),
				FakeEntity.NetworkEntity("three"),
				FakeEntity.NetworkEntity("four")
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
		db.networkDao().insert(FakeEntity.NetworkEntity("one"))
		assertNotNull(db.networkDao().get("one").firstOrNull())
		db.networkDao().delete(FakeEntity.NetworkEntity("one"))
		assertNull(db.networkDao().get("one").firstOrNull())
	}
}
