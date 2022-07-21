package com.zero.android.ui.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.zero.android.common.R.drawable
import com.zero.android.models.Network
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

@Composable
fun AppTopBar(
	modifier: Modifier = Modifier,
	network: Network?,
	openDrawer: () -> Unit,
	actions: @Composable RowScope.() -> Unit = {}
) {
	CenterAlignedTopAppBar(
		modifier = modifier,
		title = { Text(network?.displayName ?: "") },
		navigationIcon = {
			IconButton(onClick = openDrawer) {
				if (!network?.logo.isNullOrEmpty()) {
					Icon(
						painter = rememberAsyncImagePainter(network?.logo),
						contentDescription = network?.name
					)
				} else {
					Icon(
						painter = painterResource(id = drawable.ic_menu),
						contentDescription = "Menu Icon",
						tint = AppTheme.colors.surface
					)
				}
			}
		},
		actions = actions
	)
}

@Preview @Composable
fun AppTopBarPreview() = Preview {}
