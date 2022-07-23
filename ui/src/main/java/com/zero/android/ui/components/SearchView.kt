package com.zero.android.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    placeHolder: String = stringResource(R.string.search),
    showSearchCancel: Boolean = true,
    onValueChanged: (String) -> Unit,
    onSearchCancelled: () -> Unit = {},
) {
    var searchText: String by remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        CustomTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onValueChanged(searchText)
            },
            placeholderText = placeHolder,
            textStyle = Typography.bodyMedium.copy(color = AppTheme.colors.colorTextPrimary),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.weight(1f),
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_search),
                    contentDescription = "",
                    tint = AppTheme.colors.surfaceVariant
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        searchText = ""
                        onValueChanged(searchText)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cancel_24),
                        contentDescription = "",
                        tint = AppTheme.colors.surfaceVariant
                    )
                }
            }
        )
        if (showSearchCancel) {
            TextButton(
                onClick = {
                    searchText = ""
                    onValueChanged(searchText)
                    onSearchCancelled()
                }
            ) { Text(stringResource(R.string.cancel), color = AppTheme.colors.colorTextPrimary) }
        }
    }
}
