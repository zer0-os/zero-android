package com.zero.android.feature.network.drawer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NetworkDrawerRoute(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: NetworkDrawerViewModel = hiltViewModel()
) {
    NetworkDrawer(windowSizeClass = windowSizeClass, modifier = modifier)
}

//TODO: pass NavigationController and NavigationItemLists as well in [NetworkDrawer]
@Composable
fun NetworkDrawer(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.colorPrimary))
    ) {
        // Header
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = R.drawable.logo.toString(),
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(10.dp)
        )
        // Space between
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        // List of navigation items here

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Network Drawer",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Composable
fun NetworkDrawerPreview() {

}
