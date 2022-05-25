package com.zero.android.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.zero.android.feature.auth.navigation.AuthDestination
import com.zero.android.feature.auth.navigation.authGraph

@Composable
fun ZeroNavHost(
	windowSizeClass: WindowSizeClass,
	modifier: Modifier = Modifier,
	navController: NavHostController = rememberNavController(),
	startDestination: String = AuthDestination.route
) {
	NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
		authGraph(windowSizeClass = windowSizeClass)
	}
}
