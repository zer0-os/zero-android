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
import com.zero.android.navigation.HomeNavHost
import com.zero.android.ui.appbar.AppBottomBar
import com.zero.android.ui.appbar.AppTopBar
import com.zero.android.ui.components.Background
import com.zero.android.ui.sidebar.NetworkDrawerContent
import com.zero.android.ui.util.BackHandler
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
	val currentScreen by viewModel.currentScreen.collectAsState()
	val currentNetwork: Network? by viewModel.selectedNetwork.collectAsState()
	val networks: Result<List<Network>> by viewModel.networks.collectAsState()

	HomeScreen(
		viewModel = viewModel,
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
	viewModel: HomeViewModel,
	currentScreen: NavDestination,
	currentNetwork: Network?,
	networks: Result<List<Network>>,
	onNetworkSelected: (Network) -> Unit
) {
	val navController = rememberNavController()
	val scaffoldState = rememberScaffoldState()
	val coroutineScope = rememberCoroutineScope()

	val topBar: @Composable () -> Unit = {
		AppTopBar(
			network = currentNetwork,
			openDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } },
			onProfileClick = { navController.navigate(ProfileDestination.route) },
			onCreateWorldClick = { navController.navigate(ProfileDestination.route) }
		)
	}

	val bottomBar: @Composable () -> Unit = {
		AppBottomBar(
			currentDestination = currentScreen,
			onNavigateToHomeDestination = {
				coroutineScope.launch {
					viewModel.currentScreen.emit(it)
					scaffoldState.drawerState.close()
				}
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
			NetworkDrawerContent(
				modifier = modifier,
				currentNetwork = currentNetwork,
				networks = networks,
				drawerState =
				DrawerState(
					DrawerValue.valueOf(scaffoldState.drawerState.currentValue.toString())
				) {
					coroutineScope.launch {
						if (it == DrawerValue.Open) scaffoldState.drawerState.open()
						else scaffoldState.drawerState.close()
					}
					true
				},
				coroutineScope = coroutineScope,
				onNetworkSelected = onNetworkSelected,
				onNavigateToTopLevelDestination = {
					navController.navigate(it.route) { popUpTo(navController.graph.startDestinationId) }
				}
			)
		},
		drawerGesturesEnabled = scaffoldState.drawerState.isOpen
	) {
		Background { HomeNavHost(navController = navController, network = currentNetwork) }
	}
}
