package com.zero.android.ui.home

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.zero.android.common.navigation.NavDestination
import com.zero.android.common.ui.Result
import com.zero.android.feature.account.navigation.ProfileDestination
import com.zero.android.models.Network
import com.zero.android.models.fake.FakeData
import com.zero.android.navigation.HomeNavHost
import com.zero.android.ui.HomeViewModel
import com.zero.android.ui.appbar.AppBottomBar
import com.zero.android.ui.appbar.AppTopBar
import com.zero.android.ui.sidebar.NetworkDrawer
import com.zero.android.ui.util.BackHandler
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
	val currentScreen = viewModel.currentScreen
	val currentNetwork: Network by
	viewModel.selectedNetwork.collectAsState(initial = FakeData.Network())
	val networks: Result<List<Network>> by viewModel.networks.collectAsState()

	HomeScreen(
		currentScreen = currentScreen,
		currentNetwork = currentNetwork,
		networks = networks,
		onNetworkSelected = viewModel::onNetworkSelected
	)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
	modifier: Modifier = Modifier,
	currentScreen: NavDestination,
	currentNetwork: Network,
	networks: Result<List<Network>>,
	onNetworkSelected: (Network) -> Unit
) {
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

	if (scaffoldState.drawerState.isOpen) {
		BackHandler { coroutineScope.launch { scaffoldState.drawerState.close() } }
	}

	Scaffold(
		topBar = { topBar() },
		bottomBar = { bottomBar() },
		scaffoldState = scaffoldState,
		drawerContent = {
			NetworkDrawer(
				modifier = modifier,
				currentNetwork = currentNetwork,
				networks = networks,
				drawerState =
				DrawerState(
					DrawerValue.valueOf(scaffoldState.drawerState.currentValue.toString())
				) {
					true
				},
				coroutineScope = coroutineScope,
				onNetworkSelected = {
					onNetworkSelected(it)
					coroutineScope.launch { scaffoldState.drawerState.close() }
				},
				onNavigateToTopLevelDestination = {
					coroutineScope.launch { scaffoldState.drawerState.close() }
					navController.navigate(it.route) { popUpTo(navController.graph.startDestinationId) }
				}
			)
		},
		drawerGesturesEnabled = scaffoldState.drawerState.isOpen
	) {
		HomeNavHost(navController = navController)
	}
}
