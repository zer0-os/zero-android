package com.zero.android.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zero.android.common.navigation.NavDestination
import com.zero.android.ui.home.HomeRoute

object HomeDestination : NavDestination {
	override val route = "home_route"
	override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph(windowSizeClass: WindowSizeClass) {
	composable(route = HomeDestination.route) { HomeRoute(windowSizeClass) }
}
