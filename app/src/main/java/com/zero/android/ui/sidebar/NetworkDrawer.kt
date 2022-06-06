package com.zero.android.ui.sidebar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.common.R.string
import com.zero.android.common.navigation.NavDestination
import com.zero.android.common.ui.Result
import com.zero.android.feature.account.navigation.ProfileDestination
import com.zero.android.models.Network
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkDrawer(
	currentNetwork: Network,
	networks: Result<List<Network>>,
	drawerState: DrawerState,
	coroutineScope: CoroutineScope,
	onNetworkSelected: (Network) -> Unit,
	onNavigateToTopLevelDestination: (NavDestination) -> Unit
) {
	ModalNavigationDrawer(
		drawerState = drawerState,
		gesturesEnabled = drawerState.isOpen,
		drawerContainerColor = MaterialTheme.colorScheme.background,
		drawerContent = {
			DrawerContent(
				drawerState = drawerState,
				coroutineScope = coroutineScope,
				currentNetwork = currentNetwork,
				networks = networks,
				onNetworkSelected = onNetworkSelected,
				onNavigateToTopLevelDestination = onNavigateToTopLevelDestination
			)
		}
	) {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
	currentNetwork: Network,
	networks: Result<List<Network>>,
	drawerState: DrawerState,
	coroutineScope: CoroutineScope,
	onNetworkSelected: (Network) -> Unit,
	onNavigateToTopLevelDestination: (NavDestination) -> Unit
) {
	Column(modifier = Modifier.fillMaxSize()) {
		// Header
		AppDrawerHeader(
			item = currentNetwork,
			onSettingsClick = {
				coroutineScope.launch { drawerState.close() }
				onNavigateToTopLevelDestination(ProfileDestination)
			},
			onInviteClick = {
				coroutineScope.launch { drawerState.close() }
				onNavigateToTopLevelDestination(ProfileDestination)
			}
		)

		Text(text = stringResource(string.my_worlds), modifier = Modifier.fillMaxWidth().padding(12.dp))

		// Networks
		LazyRow {
			if (networks is Result.Success) {
				items(items = networks.data, key = { item -> item.id }) { network ->
					DrawerItem(
						item = network,
						onItemClick = {
							onNetworkSelected(network)
							coroutineScope.launch { drawerState.close() }
						}
					)
				}
			}
		}

		// Footer
		AppDrawerFooter(
			onCreateWorldClick = {
				coroutineScope.launch { drawerState.close() }
				onNavigateToTopLevelDestination(ProfileDestination)
			}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NetworkDrawerPreview() = Preview {
	NetworkDrawer(
		currentNetwork = FakeData.Network(),
		networks = Result.Success(FakeData.networks()),
		drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
		coroutineScope = CoroutineScope(Dispatchers.Default),
		onNetworkSelected = {},
		onNavigateToTopLevelDestination = {}
	)
}
