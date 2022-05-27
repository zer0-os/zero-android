package com.zero.android.feature.network.drawer.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zero.android.common.navigation.NavDestination
import com.zero.android.feature.network.drawer.ui.NetworkDrawerRoute

object NetworkDrawerDestination : NavDestination {
    override val route = "network_drawer_route"
    override val destination = "network_drawer_destination"
}

fun NavGraphBuilder.authGraph(windowSizeClass: WindowSizeClass) {
    composable(route = NetworkDrawerDestination.route) { NetworkDrawerRoute(windowSizeClass) }
}
