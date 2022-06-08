package com.zero.android.ui.sidebar

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
import androidx.constraintlayout.compose.ConstraintLayout
import com.zero.android.common.R.string
import com.zero.android.common.navigation.NavDestination
import com.zero.android.common.ui.Result
import com.zero.android.feature.account.navigation.ProfileDestination
import com.zero.android.models.Network
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.BODY_PADDING_HORIZONTAL
import com.zero.android.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val DRAWER_PADDING = BODY_PADDING_HORIZONTAL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkDrawer(
	modifier: Modifier = Modifier,
	currentNetwork: Network,
	networks: Result<List<Network>>,
	drawerState: DrawerState,
	coroutineScope: CoroutineScope,
	onNetworkSelected: (Network) -> Unit,
	onNavigateToTopLevelDestination: (NavDestination) -> Unit
) {
	DrawerContent(
		modifier = modifier,
		drawerState = drawerState,
		coroutineScope = coroutineScope,
		currentNetwork = currentNetwork,
		networks = networks,
		onNetworkSelected = onNetworkSelected,
		onNavigateToTopLevelDestination = onNavigateToTopLevelDestination
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
	modifier: Modifier,
	currentNetwork: Network,
	networks: Result<List<Network>>,
	drawerState: DrawerState,
	coroutineScope: CoroutineScope,
	onNetworkSelected: (Network) -> Unit,
	onNavigateToTopLevelDestination: (NavDestination) -> Unit
) {
	ConstraintLayout(modifier = modifier.fillMaxSize()) {
		val (header, worldsLabel, items, footer) = createRefs()

		AppDrawerHeader(
			modifier =
			modifier.constrainAs(header) {
				top.linkTo(parent.top)
				start.linkTo(parent.start)
			},
			network = currentNetwork,
			onSettingsClick = {
				coroutineScope.launch { drawerState.close() }
				onNavigateToTopLevelDestination(ProfileDestination)
			},
			onInviteClick = {
				coroutineScope.launch { drawerState.close() }
				onNavigateToTopLevelDestination(ProfileDestination)
			}
		)

		Text(
			text = stringResource(string.my_worlds),
			modifier =
			modifier.fillMaxWidth().padding(DRAWER_PADDING.dp).constrainAs(worldsLabel) {
				top.linkTo(header.bottom)
			},
			style = Typography.labelLarge
		)
		LazyRow(
			modifier =
			modifier.fillMaxWidth().constrainAs(items) {
				linkTo(top = worldsLabel.bottom, bottom = footer.top, bias = 0f)
			}
		) {
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

		AppDrawerFooter(
			modifier =
			modifier.constrainAs(footer) {
				bottom.linkTo(parent.bottom)
				start.linkTo(parent.start)
			},
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
