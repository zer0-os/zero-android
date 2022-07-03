package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.ProfileEntity
import com.zero.android.database.model.UserEntity
import com.zero.android.database.model.UserWithProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

	@Transaction
	@Query("SELECT * FROM users")
	fun getAll(): Flow<List<UserWithProfile>>

	@Transaction
	@Query("SELECT * FROM users WHERE id = :id")
	fun getById(id: String): Flow<UserWithProfile>

	@Transaction
	suspend fun insert(vararg data: UserWithProfile) {
		for (item in data) {
			insert(item.user)
			insert(item.profile)
		}
	}

	@Transaction @Insert
	suspend fun insert(vararg users: UserEntity)

	@Transaction @Insert
	suspend fun insert(vararg profiles: ProfileEntity)

	@Transaction @Delete
	suspend fun delete(user: UserEntity)
}
