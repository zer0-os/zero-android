package com.zero.android.ui.home

import android.annotation.SuppressLint
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
import com.zero.android.feature.account.navigation.ProfileDestination
import com.zero.android.models.fake.FakeData
import com.zero.android.navigation.HomeDestination
import com.zero.android.navigation.HomeNavHost
import com.zero.android.ui.HomeViewModel
import com.zero.android.ui.appbar.AppBottomBar
import com.zero.android.ui.appbar.AppTopBar
import com.zero.android.ui.sidebar.NetworkDrawer
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
	HomeScreen(modifier = modifier, currentScreen = HomeDestination)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, currentScreen: NavDestination) {
	val navController = rememberNavController()
	val scaffoldState = rememberScaffoldState()
	val coroutineScope = rememberCoroutineScope()

	val topBar: @Composable () -> Unit = {
		AppTopBar(
			network = FakeData.Network(),
			openDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } },
			onProfileClick = { navController.navigate(ProfileDestination.route) },
			onCreateWorldClick = { navController.navigate(ProfileDestination.route) }
		)
	}

	val bottomBar: @Composable () -> Unit = {
		AppBottomBar(
			currentDestination = currentScreen,
			onNavigateToHomeDestination = {
				coroutineScope.launch { scaffoldState.drawerState.close() }
				navController.navigate(it.route) {
					popUpTo(navController.graph.startDestinationId)
					launchSingleTop = true
				}
			}
		)
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
				coroutineScope = coroutineScope,
				onNetworkSelected = {},
				onNavigateToTopLevelDestination = {
					navController.navigate(it.route) { popUpTo(navController.graph.startDestinationId) }
				}
			)
		},
		drawerGesturesEnabled = scaffoldState.drawerState.isOpen
	) {
		HomeNavHost(navController = navController)
	}
}
