package com.zero.android.feature.network.drawer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.feature.network.drawer.R
import com.zero.android.feature.network.drawer.misc.NetworkWorld
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NetworkDrawerRoute(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: NetworkDrawerViewModel = hiltViewModel()
) {
    //NetworkDrawer(windowSizeClass = windowSizeClass, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkDrawer(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerContent = {
            DrawerContent(
                drawerState = drawerState,
                coroutineScope = coroutineScope
            )
        }
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    currentWorld: NetworkWorld = NetworkWorld(),
    networkWorlds: List<NetworkWorld> = emptyList(),
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        NetworkDrawerHeader(
            item = currentWorld,
            onSettingsClick = {
                coroutineScope.launch { drawerState.close() }
                //TODO: navigation to settings screen
            },
            onInviteClick = {
                coroutineScope.launch { drawerState.close() }
                //TODO: navigation to invite members screen
            }
        )

        Text(
            text = stringResource(R.string.my_worlds),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        //world items
        LazyRow() {
            items(items = networkWorlds, key = { item -> item.id }) { networkWorld ->
                DrawerItem(
                    item = networkWorld,
                    onItemClick = {
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        }

        //Footer
        NetworkDrawerFooter(
            onCreateWorldClick = {
                coroutineScope.launch { drawerState.close() }
                //TODO: navigation to create new world screen
            }
        )
    }
}

@Preview
@Composable
fun NetworkDrawerPreview() {

}
