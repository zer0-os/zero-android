package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.ChannelMembersCrossRef
import com.zero.android.database.model.ChannelOperatorsCrossRef
import com.zero.android.database.model.GroupChannelWithRefs
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GroupChannelDaoInterface : ChannelDaoInterface() {

	@Transaction
	@Query("SELECT * FROM channels WHERE isDirectChannel = 0 AND networkId = :networkId")
	abstract fun getByNetwork(networkId: String): Flow<List<GroupChannelWithRefs>>

	@Transaction
	@Query("SELECT * FROM channels WHERE id = :id AND isDirectChannel = 0")
	abstract fun get(id: String): Flow<GroupChannelWithRefs?>

	@Transaction
	internal open suspend fun insert(
		messageDao: MessageDao,
		memberDao: MemberDao,
		vararg data: GroupChannelWithRefs
	) = upsert(messageDao, memberDao, *data)

	@Transaction
	internal open suspend fun upsert(
		messageDao: MessageDao,
		memberDao: MemberDao,
		vararg data: GroupChannelWithRefs
	) {
		for (item in data) {
			val members = item.members.toMutableList()
			item.createdBy?.let { members.add(it) }
			memberDao.upsert(members)

			upsert(item.channel)

			item.lastMessage?.let { messageDao.upsert(it) }

			item.members
				.map { ChannelMembersCrossRef(channelId = item.channel.id, memberId = it.id) }
				.let { insert(*it.toTypedArray()) }

			item.operators
				.map { ChannelOperatorsCrossRef(channelId = item.channel.id, memberId = it.id) }
				.let { insert(*it.toTypedArray()) }
		}
	}
}
