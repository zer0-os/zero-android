package com.zero.android.feature.channel.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.zero.android.feature.channel.navigation.TOP_LEVEL_DESTINATIONS
import com.zero.android.feature.channel.navigation.TopLevelDestination
import com.zero.android.ui.theme.ZeroExtendedTheme

@Composable
fun ChannelBottomBar(
	modifier: Modifier = Modifier,
	onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
	currentDestination: NavDestination?
) {
	Surface(color = MaterialTheme.colorScheme.surface) {
		NavigationBar(
			modifier =
			Modifier.windowInsetsPadding(
				WindowInsets.safeDrawing.only(
					WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
				)
			),
			tonalElevation = 0.dp
		) {
			TOP_LEVEL_DESTINATIONS.forEach { destination ->
				val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
				NavigationBarItem(
					selected = selected,
					onClick = { onNavigateToTopLevelDestination(destination) },
					icon = {
						val showBadgeCount = true
						if (showBadgeCount) {
							BadgedBox(
								badge = {
									Badge(
										containerColor = MaterialTheme.colorScheme.primary,
										contentColor = ZeroExtendedTheme.colors.colorTextPrimary
									) { Text("01") }
								}
							) { BottomBarIcon(isSelected = selected, destination = destination) }
						} else {
							BottomBarIcon(isSelected = selected, destination = destination)
						}
					}
				)
			}
		}
	}
}

@Composable
fun BottomBarIcon(isSelected: Boolean, destination: TopLevelDestination) {
	Icon(
		if (isSelected) {
			painterResource(destination.selectedIcon)
		} else {
			painterResource(destination.unselectedIcon)
		},
		contentDescription = null
	)
}

@Preview @Composable
fun ChannelBottomBarPreview() {}
