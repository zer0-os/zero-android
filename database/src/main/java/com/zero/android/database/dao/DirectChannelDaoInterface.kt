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
	) = upsert(messageDao, memberDao, *data)

	@Transaction
	internal open suspend fun upsert(
		messageDao: MessageDao,
		memberDao: MemberDao,
		vararg data: DirectChannelWithRefs
	) {
		for (item in data) {
			upsert(item.channel)
			item.lastMessage?.let { messageDao.upsert(it) }

			memberDao.upsert(item.members)
			item.members
				.map { ChannelMembersCrossRef(channelId = item.channel.id, memberId = it.id) }
				.let { insert(*it.toTypedArray()) }
		}
	}
}
