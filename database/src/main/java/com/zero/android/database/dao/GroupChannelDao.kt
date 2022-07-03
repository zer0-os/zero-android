package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.GroupChannelWithRefs
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupChannelDao {

	@Transaction
	@Query("SELECT * FROM channels WHERE isDirectChannel = 0")
	fun getChannels(): Flow<List<GroupChannelWithRefs>>

	@Transaction
	@Query("SELECT * FROM channels WHERE id = :id AND isDirectChannel = 0")
	fun getChannelById(id: String): Flow<GroupChannelWithRefs>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(vararg channels: ChannelEntity)

	@Delete suspend fun delete(channel: ChannelEntity)
}
