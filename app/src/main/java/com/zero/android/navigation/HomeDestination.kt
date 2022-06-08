package com.zero.android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zero.android.common.navigation.NavDestination
import com.zero.android.ui.home.HomeRoute

object HomeDestination : NavDestination {
	override val route = "home_route"
	override val destination = "home_destination"
}

internal fun NavGraphBuilder.homeGraph(onLogout: () -> Unit) {
	composable(route = HomeDestination.route) { HomeRoute() }
}
