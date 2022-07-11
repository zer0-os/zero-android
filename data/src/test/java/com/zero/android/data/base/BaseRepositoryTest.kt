package com.zero.android.data.base

import com.zero.android.database.dao.ChannelDao
import com.zero.android.database.dao.DirectChannelDaoInterface
import com.zero.android.database.dao.GroupChannelDaoInterface
import com.zero.android.database.dao.MemberDao
import com.zero.android.database.dao.MessageDao
import com.zero.android.database.dao.MessageDaoInterface
import com.zero.android.network.service.ChannelService
import org.mockito.Mockito

abstract class BaseRepositoryTest {

	protected val mockDirectChannelDao = Mockito.mock(DirectChannelDaoInterface::class.java)
	protected val mockGroupChannelDao = Mockito.mock(GroupChannelDaoInterface::class.java)
	protected val mockMemberDao = Mockito.mock(MemberDao::class.java)
	protected val mockMessageDaoInterface = Mockito.mock(MessageDaoInterface::class.java)
	protected val mockMessageDao =
		MessageDao(messageDao = mockMessageDaoInterface, memberDao = mockMemberDao)
	protected val mockChannelDao =
		ChannelDao(
			directChannelDao = mockDirectChannelDao,
			groupChannelDao = mockGroupChannelDao,
			memberDao = mockMemberDao,
			messageDao = mockMessageDao
		)
	protected val mockChannelService = Mockito.mock(ChannelService::class.java)
}
