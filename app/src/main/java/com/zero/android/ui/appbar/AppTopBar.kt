package com.zero.android.ui.appbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.zero.android.common.R
import com.zero.android.common.R.drawable
import com.zero.android.models.Network
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

@Composable
fun AppTopBar(
	modifier: Modifier = Modifier,
	network: Network?,
	openDrawer: () -> Unit,
	onProfileClick: () -> Unit,
	onCreateWorldClick: () -> Unit
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
					Icon(painter = painterResource(id = drawable.ic_menu), contentDescription = "Menu Icon", tint = AppTheme.colors.surface)
				}
			}
		},
		actions = {
			IconButton(onClick = onProfileClick) {
				Icon(
					painter = painterResource(drawable.img_profile_avatar),
					contentDescription = stringResource(R.string.profile)
				)
			}
			IconButton(onClick = onCreateWorldClick) {
				Icon(
					painter = painterResource(drawable.ic_add_circle),
					contentDescription = stringResource(R.string.cd_ic_circle_add)
				)
			}
		},
	)
}

@Preview
@Composable
fun AppTopBarPreview() = Preview {
	AppTopBar(
		network = FakeData.Network(),
		openDrawer = {},
		onProfileClick = {},
		onCreateWorldClick = {}
	)
}
