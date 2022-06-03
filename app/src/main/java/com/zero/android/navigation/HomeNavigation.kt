package com.zero.android.navigation

import com.zero.android.common.R.drawable

val HOME_DESTINATIONS =
	listOf(
		AppBarNavigation(
			HomeDestination.route,
			drawable.ic_channel_selected,
			drawable.ic_channel_unselected
		),
		AppBarNavigation("{ROUTE_PEOPLE}", drawable.ic_people, drawable.ic_people),
		AppBarNavigation("{ROUTE_FEEDS}", drawable.ic_feeds, drawable.ic_feeds),
		AppBarNavigation(
			"{ROUTE_NOTIFICATIONS}",
			drawable.ic_notification_selected,
			drawable.ic_notification_unselected
		),
		AppBarNavigation(
			"{ROUTE_DIRECT_CHAT}",
			drawable.ic_direct_chat_selected,
			drawable.ic_direct_chat_unselected
		)
	)
