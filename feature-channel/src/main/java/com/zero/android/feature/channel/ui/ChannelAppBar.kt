package com.zero.android.feature.channel.ui

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
import com.zero.android.feature.network.drawer.R
import com.zero.android.feature.network.drawer.misc.NetworkWorld

@Composable
fun ChannelAppBar(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    networkWorld: NetworkWorld,
    onProfileClick: () -> Unit,
    onCreateWorldClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(networkWorld.title)
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    painter = rememberAsyncImagePainter(networkWorld.icon),
                    contentDescription = networkWorld.title
                )
            }
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    painter = painterResource(com.zero.android.feature.channel.R.drawable.img_profile_avatar),
                    contentDescription = stringResource(com.zero.android.feature.channel.R.string.profile),
                )
            }
            IconButton(onClick = onCreateWorldClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_circle_add),
                    contentDescription = stringResource(R.string.cd_ic_circle_add),
                )
            }
        }
    )
}

@Preview
@Composable
fun ChannelAppBarPreview() {

}
