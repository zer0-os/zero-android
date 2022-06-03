package com.zero.android.feature.channel.navigation

import com.zero.android.feature.channel.R

data class TopLevelDestination(val route: String, val selectedIcon: Int, val unselectedIcon: Int)

val TOP_LEVEL_DESTINATIONS =
	listOf(
		TopLevelDestination(
			ChannelDestination.route,
			R.drawable.ic_channel_selected,
			R.drawable.ic_channel_unselected
		),
		TopLevelDestination("{ROUTE_PEOPLE}", R.drawable.ic_people, R.drawable.ic_people),
		TopLevelDestination(
			"{ROUTE_FEEDS}",
			R.drawable.ic_feeds_selected,
			R.drawable.ic_feeds_unselected
		),
		TopLevelDestination(
			"{ROUTE_NOTIFICATIONS}",
			R.drawable.ic_notification_selected,
			R.drawable.ic_notification_unselected
		),
		TopLevelDestination(
			"{ROUTE_DIRECT_CHAT}",
			R.drawable.ic_direct_chat_selected,
			R.drawable.ic_direct_chat_unselected
		)
	)
