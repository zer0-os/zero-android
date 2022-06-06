package com.zero.android.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zero.android.feature.account.navigation.NotificationsDestination
import com.zero.android.feature.account.ui.notifications.NotificationsRoute
import com.zero.android.feature.auth.navigation.AuthDestination
import com.zero.android.feature.auth.navigation.authGraph
import com.zero.android.feature.channels.navigation.ChannelsDestination
import com.zero.android.feature.channels.ui.channels.ChannelsRoute
import com.zero.android.feature.feed.FeedRoute
import com.zero.android.feature.feed.navigation.FeedDestination
import com.zero.android.feature.messages.navigation.MessagesDestination
import com.zero.android.feature.messages.ui.messages.MessagesRoute
import com.zero.android.feature.people.MembersRoute
import com.zero.android.feature.people.navigation.MembersDestination

internal fun NavGraphBuilder.onboardGraph(controller: NavController) {
	authGraph(onLogin = { controller.navigate(HomeDestination.route) { popUpTo(0) } })
	homeGraph(onLogout = { controller.navigate(AuthDestination.route) { popUpTo(0) } })
}

internal fun NavGraphBuilder.appGraph(controller: NavController) {
	composable(route = ChannelsDestination.route) { ChannelsRoute() }
	composable(route = MembersDestination.route) { MembersRoute() }
	composable(route = FeedDestination.route) { FeedRoute() }
	composable(route = NotificationsDestination.route) { NotificationsRoute() }
	composable(route = MessagesDestination.route) { MessagesRoute() }
}
