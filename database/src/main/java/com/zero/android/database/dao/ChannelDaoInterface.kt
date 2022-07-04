package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.zero.android.database.model.ChannelAuthorCrossRef
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.ChannelMembersCrossRef
import com.zero.android.database.model.ChannelOperatorsCrossRef

@Dao
abstract class ChannelDaoInterface {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	protected abstract suspend fun insert(vararg channels: ChannelEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	protected abstract suspend fun insert(vararg refs: ChannelAuthorCrossRef)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	protected abstract suspend fun insert(vararg refs: ChannelMembersCrossRef)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	protected abstract suspend fun insert(vararg refs: ChannelOperatorsCrossRef)

	@Delete abstract suspend fun delete(channel: ChannelEntity)
}
