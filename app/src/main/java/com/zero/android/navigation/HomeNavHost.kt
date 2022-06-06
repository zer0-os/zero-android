package com.zero.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun HomeNavHost(navController: NavController) {
	NavHost(
		navController = navController as NavHostController,
		startDestination = HomeDestination.route
	) { appGraph(navController) }
}
