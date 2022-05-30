package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zero.android.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

	@Query("SELECT * FROM users")
	fun getAll(): Flow<List<UserEntity>>

	@Query("SELECT * FROM users WHERE id = :id")
	fun getById(id: String): Flow<UserEntity>

	@Insert suspend fun insert(vararg users: UserEntity)

	@Delete suspend fun delete(user: UserEntity)
}
