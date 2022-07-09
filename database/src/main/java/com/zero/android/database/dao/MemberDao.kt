package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MemberDao : BaseDao<MemberEntity>() {

	@Transaction
	@Query("SELECT * FROM members")
	abstract suspend fun getAll(): List<MemberEntity>

	@Transaction
	@Query("SELECT * FROM members WHERE id = :id")
	abstract fun get(id: String): Flow<MemberEntity?>
}
