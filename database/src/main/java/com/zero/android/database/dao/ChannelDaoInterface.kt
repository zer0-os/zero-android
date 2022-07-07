package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.ChannelMembersCrossRef
import com.zero.android.database.model.ChannelOperatorsCrossRef

@Dao
abstract class ChannelDaoInterface : BaseDao<ChannelEntity>() {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	protected abstract suspend fun insert(vararg refs: ChannelMembersCrossRef)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	protected abstract suspend fun insert(vararg refs: ChannelOperatorsCrossRef)
}
