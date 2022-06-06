package com.zero.android.ui.home

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.zero.android.common.navigation.NavDestination
import com.zero.android.models.fake.FakeData
import com.zero.android.navigation.HomeDestination
import com.zero.android.navigation.HomeNavHost
import com.zero.android.ui.HomeViewModel
import com.zero.android.ui.appbar.AppBottomBar
import com.zero.android.ui.appbar.AppTopBar
import com.zero.android.ui.sidebar.NetworkDrawer

@Composable
fun HomeRoute(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
	HomeScreen(modifier = modifier, currentScreen = HomeDestination)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, currentScreen: NavDestination) {
	val navController = rememberNavController()
	val scaffoldState = rememberScaffoldState()
	val coroutineScope = rememberCoroutineScope()

	val topBar: @Composable () -> Unit = {
		AppTopBar(
			network = FakeData.Network(),
			openDrawer = {},
			onProfileClick = {},
			onCreateWorldClick = {}
		)
	}

	val bottomBar: @Composable () -> Unit = {
		AppBottomBar(currentDestination = currentScreen, onNavigateToTopLevelDestination = {})
	}

	Scaffold(
		topBar = { topBar() },
		bottomBar = { bottomBar() },
		scaffoldState = scaffoldState,
		drawerContent = {
			NetworkDrawer(
				currentNetwork = FakeData.Network(),
				networks = FakeData.networks(),
				drawerState =
				DrawerState(
					DrawerValue.valueOf(scaffoldState.drawerState.currentValue.toString())
				) {
					true
				},
				coroutineScope = coroutineScope
			)
		},
		drawerGesturesEnabled = scaffoldState.drawerState.isOpen
	) { _ ->
		HomeNavHost(navController = navController)
	}
}
