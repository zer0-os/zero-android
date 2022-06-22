package com.zero.android.database.dao

import androidx.room.*
import com.zero.android.database.model.UserAndProfileRelation
import com.zero.android.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<UserAndProfileRelation>>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: String): Flow<UserAndProfileRelation>

    @Transaction @Insert
    suspend fun insert(vararg users: UserEntity)

    @Transaction @Delete
    suspend fun delete(user: UserEntity)
}
