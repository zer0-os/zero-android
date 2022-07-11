package com.zero.android.ui.sidebar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zero.android.common.R
import com.zero.android.models.Network
import com.zero.android.models.fake.FakeModel
import com.zero.android.ui.components.CountBadge
import com.zero.android.ui.components.MediumCircularImage
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun DrawerItem(modifier: Modifier = Modifier, item: Network, onItemClick: (Network) -> Unit) {
	ConstraintLayout(
		modifier =
		modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.padding(horizontal = 12.dp, vertical = 6.dp)
			.clickable { onItemClick.invoke(item) }
	) {
		val (image, textTop, textBottom, textEnd) = createRefs()

		MediumCircularImage(
			modifier =
			modifier
				.constrainAs(image) {
					top.linkTo(parent.top)
					bottom.linkTo(parent.bottom)
					start.linkTo(parent.start)
				}
				.padding(end = 8.dp),
			placeHolder = R.drawable.ic_circular_image_placeholder,
			imageUrl = item.logo,
			contentDescription = item.name
		)
		Text(
			text = item.displayName,
			modifier =
			modifier.constrainAs(textTop) {
				top.linkTo(parent.top)
				bottom.linkTo(textBottom.top)
				linkTo(start = image.end, end = textEnd.start, bias = 0f)
			},
			color = AppTheme.colors.colorTextPrimary,
			style = Typography.bodyLarge
		)
		Text(
			text = item.displayName,
			modifier =
			modifier.constrainAs(textBottom) {
				top.linkTo(textTop.bottom)
				bottom.linkTo(parent.bottom)
				start.linkTo(textTop.start)
				end.linkTo(textTop.end)
				width = Dimension.fillToConstraints
			},
			color = AppTheme.colors.colorTextSecondaryVariant,
			style = Typography.bodyMedium
		)
		if (item.unreadCount > 0) {
			CountBadge(
				count = item.unreadCount,
				modifier =
				modifier.constrainAs(textEnd) {
					top.linkTo(parent.top)
					bottom.linkTo(parent.bottom)
					end.linkTo(parent.end)
				}
			)
		}
	}
}

@Preview(showBackground = false)
@Composable
fun DrawerItemPreview() = Preview { DrawerItem(item = FakeModel.Network(), onItemClick = {}) }
