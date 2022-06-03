package com.zero.android.ui.sidebar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zero.android.common.R.drawable
import com.zero.android.common.R.string
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.ZeroExtendedTheme

@Composable
fun AppDrawerFooter(modifier: Modifier = Modifier, onCreateWorldClick: () -> Unit) {
	Column(
		modifier = Modifier.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceBetween
	) {
		Divider(
			color = ZeroExtendedTheme.colors.buttonSecondary,
			modifier = Modifier.fillMaxWidth(),
			thickness = 1.dp
		)
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			Image(
				painter = painterResource(drawable.ic_add_circle),
				contentDescription = stringResource(string.cd_ic_circle_add),
				contentScale = ContentScale.Fit,
				modifier = Modifier.wrapContentSize().clickable(onClick = onCreateWorldClick)
			)
			Text(
				stringResource(string.create_a_world),
				fontSize = 16.sp,
				color = ZeroExtendedTheme.colors.buttonSecondary
			)
		}
	}
}

@Preview @Composable
fun NetworkDrawerFooterPreview() = Preview { AppDrawerFooter {} }
