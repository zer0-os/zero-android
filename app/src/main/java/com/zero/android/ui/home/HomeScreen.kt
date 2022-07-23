package com.zero.android.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.zero.android.common.R
import com.zero.android.common.navigation.NavDestination
import com.zero.android.common.ui.Result
import com.zero.android.feature.channels.navigation.ChannelsDestination
import com.zero.android.feature.channels.ui.directchannels.DirectChannelDestination
import com.zero.android.models.Network
import com.zero.android.navigation.HomeNavHost
import com.zero.android.ui.appbar.AppBottomBar
import com.zero.android.ui.appbar.AppTopBar
import com.zero.android.ui.appbar.HOME_DESTINATIONS
import com.zero.android.ui.components.Background
import com.zero.android.ui.sidebar.NetworkDrawerContent
import com.zero.android.ui.theme.AppTheme
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
		onNetworkSelected = viewModel::onNetworkSelected,
		onTriggerSearch = { viewModel.triggerSearch(it) }
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
	onNetworkSelected: (Network) -> Unit,
    onTriggerSearch: (Boolean) -> Unit
) {
	val navController = rememberNavController()
	val scaffoldState = rememberScaffoldState()
	val coroutineScope = rememberCoroutineScope()

	var isRootDestination by remember { mutableStateOf(true) }
	var showMenu by remember { mutableStateOf(false) }

	navController.addOnDestinationChangedListener { _, destination, _ ->
        onTriggerSearch(false)
		isRootDestination = HOME_DESTINATIONS.map { it.destination.route }.contains(destination.route)
	}

	val actionItems: @Composable RowScope.() -> Unit = {
		if (currentScreen == ChannelsDestination || currentScreen == DirectChannelDestination) {
			IconButton(onClick = { onTriggerSearch(true) }, modifier = Modifier.size(32.dp)) {
				Image(
					painter = painterResource(R.drawable.ic_search),
					contentDescription = stringResource(R.string.search_channels)
				)
			}
			IconButton(onClick = { showMenu = !showMenu }) {
				Icon(Icons.Default.MoreVert, contentDescription = "")
			}
			DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
				DropdownMenuItem(
					text = { Text(text = stringResource(R.string.profile)) },
					onClick = {},
					leadingIcon = {
						Image(
							painter = painterResource(R.drawable.img_profile_avatar),
							contentDescription = stringResource(R.string.profile)
						)
					}
				)
				DropdownMenuItem(
					text = { Text(text = stringResource(R.string.create_a_world)) },
					onClick = {},
					leadingIcon = {
						IconButton(
							onClick = {},
							modifier = Modifier.border(1.dp, AppTheme.colors.glow, CircleShape).size(32.dp)
						) {
							Icon(
								imageVector = Icons.Filled.Add,
								contentDescription = stringResource(R.string.create_a_world)
							)
						}
					}
				)
			}
		} else {
			IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
				Image(
					painter = painterResource(R.drawable.img_profile_avatar),
					contentDescription = stringResource(R.string.profile)
				)
			}
			Spacer(modifier = Modifier.padding(4.dp))
			IconButton(
				onClick = {},
				modifier = Modifier.border(1.dp, AppTheme.colors.glow, CircleShape).size(32.dp)
			) {
				Icon(
					imageVector = Icons.Filled.Add,
					contentDescription = stringResource(R.string.create_a_world)
				)
			}
			Spacer(modifier = Modifier.padding(4.dp))
		}
	}

	val topBar: @Composable () -> Unit = {
		AppTopBar(
			network = currentNetwork,
			openDrawer = { coroutineScope.launch { scaffoldState.drawerState.open() } },
			actions = actionItems
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
		topBar = {
			if (isRootDestination) {
				topBar()
			}
		},
		bottomBar = {
			if (isRootDestination) {
				bottomBar()
			}
		},
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
				onNetworkSelected = {
					onNetworkSelected(it)
					coroutineScope.launch { scaffoldState.drawerState.close() }
				},
				onNavigateToTopLevelDestination = {
					navController.navigate(it.route) { popUpTo(navController.graph.startDestinationId) }
				}
			)
		},
		drawerGesturesEnabled = scaffoldState.drawerState.isOpen
	) { innerPadding ->
		Background {
			Box(modifier = Modifier.padding(innerPadding)) {
				HomeNavHost(navController = navController, network = currentNetwork)
			}
		}
	}
}
