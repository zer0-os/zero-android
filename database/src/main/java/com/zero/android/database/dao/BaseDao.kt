package com.zero.android.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import com.zero.android.database.model.BaseEntity

abstract class BaseDao<T : BaseEntity> {

	@Transaction
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	internal abstract suspend fun insert(item: T): Long

	@Transaction
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	internal abstract suspend fun insert(items: List<T>): List<Long>

	@Transaction
	@Update(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun update(item: T)

	@Transaction
	@Update(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun update(items: List<T>)

	@Transaction
	open suspend fun upsert(item: T) {
		if (insert(item) == -1L) update(item)
	}

	@Transaction
	open suspend fun upsert(items: List<T>) {
		insert(items)
			.mapIndexedNotNull { index, rowID -> if (rowID == -1L) items[index] else null }
			.let { update(it) }
	}

	@Delete abstract suspend fun delete(item: T)

	@Delete abstract suspend fun delete(item: List<T>)
}
