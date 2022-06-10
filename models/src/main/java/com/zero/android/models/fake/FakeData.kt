package com.zero.android.models.fake

import com.zero.android.models.Network
import com.zero.android.models.fake.channel.ChannelRowMessage

object FakeData {

    fun Network(id: String = "id") =
        Network(
            id = id,
            name = "display.name",
            displayName = "Display Name",
            isPublic = true,
            unreadCount = 4
        )

    fun networks() = listOf(Network("one"), Network("two"), Network("three"))

    fun channelTabs() = emptyList<ChannelTab>()

    fun channelMessages() = emptyList<ChannelRowMessage>()
}
