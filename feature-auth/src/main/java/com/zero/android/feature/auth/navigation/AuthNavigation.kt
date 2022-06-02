package com.zero.android.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zero.android.common.navigation.NavDestination
import com.zero.android.feature.auth.AuthRoute

object AuthDestination : NavDestination {
	override val route = "login_route"
	override val destination = "login_destination"
}

fun NavGraphBuilder.authGraph(onLogin: () -> Unit) {
	composable(route = AuthDestination.route) { AuthRoute(onLogin = onLogin) }
}
