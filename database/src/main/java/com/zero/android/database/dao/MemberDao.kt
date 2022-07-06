package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zero.android.database.model.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

	@Transaction
	@Query("SELECT * FROM members WHERE id = :id")
	fun getById(id: String): Flow<MemberEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(vararg members: MemberEntity)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun update(vararg users: MemberEntity)

	@Delete suspend fun delete(vararg member: MemberEntity)
}
