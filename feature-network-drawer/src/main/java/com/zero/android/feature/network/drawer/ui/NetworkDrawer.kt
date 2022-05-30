package com.zero.android.feature.network.drawer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.feature.network.drawer.misc.NetworkWorld

@Composable
fun NetworkDrawerRoute(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: NetworkDrawerViewModel = hiltViewModel()
) {
    NetworkDrawer(windowSizeClass = windowSizeClass, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkDrawer(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerContent = { DrawerContent() }
    ) {

    }
}

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    currentWorld: NetworkWorld = NetworkWorld(),
    networkWorlds: List<NetworkWorld> = emptyList(),
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        NetworkDrawerHeader(
            item = currentWorld,
            onSettingsClick = {
                //TODO: navigation to settings screen
            },
            onInviteClick = {
                //TODO: navigation to invite members screen
            }
        )

        //world items
        LazyRow() {
            items(items = networkWorlds, key = { item -> item.id }) { networkWorld ->
                DrawerItem(
                    item = networkWorld,
                    onItemClick = {

                    }
                )
            }
        }

        //Footer
        NetworkDrawerFooter(
            onCreateWorldClick = {
                //TODO: navigation to create new world screen
            }
        )
    }
}

@Preview
@Composable
fun NetworkDrawerPreview() {

}
