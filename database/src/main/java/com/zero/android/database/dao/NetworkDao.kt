package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zero.android.database.model.NetworkEntity
import com.zero.android.models.ChannelCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface NetworkDao {

	@Transaction
	@Query("SELECT * FROM networks")
	fun getAll(): Flow<List<NetworkEntity>>

	@Transaction
	@Query("SELECT * FROM networks WHERE id = :id")
	fun getById(id: String): Flow<NetworkEntity>

	@Query("SELECT DISTINCT category from channels WHERE networkId = :id AND isDirectChannel = 0")
	fun getCategories(id: String): Flow<List<ChannelCategory>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(vararg networks: NetworkEntity)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun update(vararg network: NetworkEntity)

	@Delete suspend fun delete(network: NetworkEntity)
}
