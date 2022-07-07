package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zero.android.database.model.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

	@Transaction
	@Query("SELECT * FROM profiles WHERE userId = :userId")
	fun get(userId: String): Flow<ProfileEntity>

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun update(vararg profiles: ProfileEntity)
}
