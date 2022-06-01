package com.zero.android.ui

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zero.android.navigation.ZeroNavHost
import com.zero.android.ui.theme.ZeroTheme

@Composable
fun AppLayout(
	windowSizeClass: WindowSizeClass,
	modifier: Modifier = Modifier,
	viewModel: AppViewModel = hiltViewModel()
) {
	ZeroTheme {
		val navController = rememberNavController()
		val navBackStackEntry = navController.currentBackStackEntryAsState()
		val currentRoute =
			navBackStackEntry.value?.destination?.route ?: viewModel.startDestination.route

		ZeroNavHost(
			windowSizeClass = windowSizeClass,
			navController = navController,
			modifier = modifier.systemBarsPadding(),
			startDestination = currentRoute
		)
	}
}
