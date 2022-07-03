package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

	@Transaction
	@Query("SELECT * FROM profiles WHERE userId = :userId")
	fun getByUser(userId: String): Flow<ProfileEntity>
}
