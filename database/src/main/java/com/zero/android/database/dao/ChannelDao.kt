package com.zero.android.database.dao

import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.DirectChannelWithRefs
import com.zero.android.database.model.GroupChannelWithRefs
import javax.inject.Inject

class ChannelDao
@Inject
constructor(
	private val directChannelDao: DirectChannelDaoInterface,
	private val groupChannelDao: GroupChannelDaoInterface,
	private val memberDao: MemberDao,
	private val messageDao: MessageDao
) {

	fun getGroupChannels(networkId: String) = groupChannelDao.getByNetwork(networkId)

	fun getDirectChannels() = directChannelDao.getAll()

	fun getGroupChannel(id: String) = groupChannelDao.get(id)

	fun getDirectChannel(id: String) = directChannelDao.get(id)

	suspend fun upsert(vararg data: DirectChannelWithRefs) =
		directChannelDao.upsert(messageDao, memberDao, *data)

	suspend fun upsert(vararg data: GroupChannelWithRefs) =
		groupChannelDao.upsert(messageDao, memberDao, *data)

	suspend fun delete(entity: ChannelEntity) = groupChannelDao.delete(entity)
}
