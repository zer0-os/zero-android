package com.zero.android.database.dao

import androidx.room.*
import com.zero.android.database.model.NetworkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NetworkDao {

	@Transaction
	@Query("SELECT * FROM networks")
	fun getAll(): Flow<List<NetworkEntity>>

	@Transaction
	@Query("SELECT * FROM networks WHERE id = :id")
	fun getById(id: String): Flow<NetworkEntity>

	@Insert suspend fun insert(vararg users: NetworkEntity)

	@Delete suspend fun delete(user: NetworkEntity)
}
