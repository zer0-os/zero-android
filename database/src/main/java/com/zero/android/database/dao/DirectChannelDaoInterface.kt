package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.ChannelMembersCrossRef
import com.zero.android.database.model.DirectChannelWithRefs
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DirectChannelDaoInterface : ChannelDaoInterface() {

	@Transaction
	@Query("SELECT * FROM channels WHERE isDirectChannel = 1")
	abstract fun getAll(): Flow<List<DirectChannelWithRefs>>

	@Transaction
	@Query("SELECT * FROM channels WHERE id = :id AND isDirectChannel = 1")
	abstract fun getById(id: String): Flow<DirectChannelWithRefs>

	@Transaction
	internal open suspend fun insert(
		messageDao: MessageDao,
		memberDao: MemberDao,
		vararg data: DirectChannelWithRefs
	) {
		for (item in data) {
			insert(item.channel)
			memberDao.insert(*item.members.toTypedArray())
			item.lastMessage?.let { messageDao.insert(it) }

			item.members
				.map { ChannelMembersCrossRef(channelId = item.channel.id, memberId = it.id) }
				.let { insert(*it.toTypedArray()) }
		}
	}
}
