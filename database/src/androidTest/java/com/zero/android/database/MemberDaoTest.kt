package com.zero.android.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.model.MemberEntity
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MemberDaoTest : BaseDatabaseTest() {

	private val member = MemberEntity(id = "memberId")

	@Test
	fun insertMember() = runTest {
		db.memberDao().insert(member)
		db.memberDao().insert(member) // Checking 2nd insert

		assertNotNull(db.memberDao().get(member.id))
	}

	@Test
	fun updateMember() = runTest {
		db.memberDao().insert(member)
		db.memberDao().update(MemberEntity(id = member.id, name = "Name"))

		val data = db.memberDao().get(member.id).first()
		assertEquals("Name", data.name)
	}

	@Test
	fun deleteMember() = runTest {
		db.memberDao().insert(member)
		db.memberDao().delete(member)

		assertNull(db.memberDao().get(member.id).firstOrNull())
	}
}
