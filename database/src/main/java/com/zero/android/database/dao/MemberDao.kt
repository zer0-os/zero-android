package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.MemberEntity
import com.zero.android.database.model.MessageMentionCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

	@Transaction
	@Query("SELECT * FROM members WHERE id = :id")
	fun getById(id: String): Flow<MemberEntity>

	@Query("SELECT * FROM message_mentions_relation")
	fun test(): Flow<MessageMentionCrossRef>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(vararg members: MemberEntity)

	@Delete suspend fun delete(member: MemberEntity)
}
