package com.zero.android.feature.channel.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zero.android.common.navigation.NavDestination
import com.zero.android.feature.channel.ui.ChannelRoute

object ChannelDestination : NavDestination {
    override val route = "channel_route"
    override val destination = "channel_destination"
}

fun NavGraphBuilder.authGraph(windowSizeClass: WindowSizeClass) {
    composable(route = ChannelDestination.route) { ChannelRoute(windowSizeClass) }
}
