package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zero.android.database.model.ProfileEntity
import com.zero.android.database.model.UserEntity
import com.zero.android.database.model.UserWithProfile
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {

	@Transaction
	@Query("SELECT * FROM users")
	abstract fun getAll(): Flow<List<UserWithProfile>>

	@Transaction
	@Query("SELECT * FROM users WHERE id = :id")
	abstract fun get(id: String): Flow<UserWithProfile>

	@Transaction
	open suspend fun insert(data: UserWithProfile) {
		insert(data.user)
		insert(data.profile)
	}

	@Transaction
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insert(user: UserEntity)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun update(user: UserEntity)

	@Transaction
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	internal abstract suspend fun insert(profile: ProfileEntity)

	@Transaction @Delete
	abstract suspend fun delete(user: UserEntity)
}
