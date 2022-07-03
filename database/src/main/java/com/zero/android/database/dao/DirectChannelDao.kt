package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.DirectChannelWithRefs
import kotlinx.coroutines.flow.Flow

@Dao
interface DirectChannelDao {

	@Transaction
	@Query("SELECT * FROM channels WHERE isDirectChannel = 1")
	fun getChannels(): Flow<List<DirectChannelWithRefs>>

	@Transaction
	@Query("SELECT * FROM channels WHERE id = :id AND isDirectChannel = 1")
	fun getChannelById(id: String): Flow<DirectChannelWithRefs>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(vararg channels: ChannelEntity)

	@Delete suspend fun delete(channel: ChannelEntity)
}
