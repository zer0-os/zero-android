package com.zero.android.feature.messages.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zero.android.common.navigation.NavDestination
import com.zero.android.feature.messages.ui.messages.MessagesRoute

object MessagesDestination : NavDestination {
	override val route = "messages_route"
	override val destination = "messages_destination"

	const val channelIdArg = "channelId"
	const val channelTypeArg = "isGroupChannel"
}

fun NavGraphBuilder.chatGraph() {
	composable(
		route = "${MessagesDestination.route}/{${MessagesDestination.channelIdArg}}/{${MessagesDestination.channelTypeArg}}",
		arguments = listOf(
			navArgument(MessagesDestination.channelIdArg) {
				type = NavType.StringType
			},
			navArgument(MessagesDestination.channelTypeArg) {
				type = NavType.BoolType
			}
		)
	) { MessagesRoute() }
}
