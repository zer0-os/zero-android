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
import com.zero.android.common.R.drawable
import com.zero.android.common.R.string
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun AppDrawerFooter(modifier: Modifier = Modifier, onCreateWorldClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Divider(
            color = AppTheme.colors.divider,
            modifier = modifier.fillMaxWidth(),
            thickness = 0.5.dp
        )
        Row(
            modifier =
			modifier
				.fillMaxWidth()
				.padding(DRAWER_PADDING.dp)
				.clickable(onClick = onCreateWorldClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(drawable.ic_add_circle),
                contentDescription = stringResource(string.cd_ic_circle_add),
                contentScale = ContentScale.Fit,
                modifier = modifier
					.wrapContentSize()
					.padding(end = 8.dp)
            )
            Text(
                stringResource(string.create_a_world),
                style = Typography.bodyLarge,
                color = AppTheme.colors.colorTextSecondaryVariant
            )
        }
    }
}

@Preview
@Composable
fun NetworkDrawerFooterPreview() = Preview { AppDrawerFooter {} }
