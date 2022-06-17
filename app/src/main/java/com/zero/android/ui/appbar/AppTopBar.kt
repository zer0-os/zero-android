package com.zero.android.ui.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
			IconButton(
                onClick = onProfileClick,
                modifier = Modifier.size(32.dp)
            ) {
				Image(
					painter = painterResource(drawable.img_profile_avatar),
					contentDescription = stringResource(R.string.profile)
				)
			}
            Spacer(modifier = Modifier.padding(2.dp))
            IconButton(
                onClick = onCreateWorldClick,
                modifier = Modifier
                    .border(1.dp, AppTheme.colors.glow, CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.create_a_world),
                )
            }
            Spacer(modifier = Modifier.padding(2.dp))
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
