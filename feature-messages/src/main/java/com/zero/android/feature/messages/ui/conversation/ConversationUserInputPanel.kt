package com.zero.android.feature.messages.ui.conversation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.feature.messages.helper.BackPressHandler
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

enum class InputSelector {
    TEXT,
    ATTACHMENT,
    IMAGE,
    VOICE_MEMO
}

@Preview
@Composable
fun UserInputPreview() {
    UserInputPanel(onMessageSent = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInputPanel(
    modifier: Modifier = Modifier,
    onMessageSent: (String) -> Unit,
    resetScroll: () -> Unit = {},
    addAttachment: () -> Unit = {},
    addImage: () -> Unit = {},
    recordMemo: () -> Unit = {},
) {
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.TEXT) }
    val dismissKeyboard = { currentInputSelector = InputSelector.TEXT }

    // Intercept back navigation if there's a InputSelector visible
    if (currentInputSelector != InputSelector.TEXT) {
        BackPressHandler(onBackPressed = dismissKeyboard)
    }

    var textState by remember { mutableStateOf(TextFieldValue()) }

    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth()
    ) {
        IconButton(modifier = Modifier.align(CenterVertically), onClick = {
            currentInputSelector = InputSelector.ATTACHMENT
            addAttachment()
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "cd_add_attachment")
        }
        UserInputText(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .align(CenterVertically),
            textFieldValue = textState,
            onTextChanged = { textState = it },
            // Only show the keyboard if there's no input selector and text field has focus
            keyboardShown = currentInputSelector == InputSelector.TEXT && textFieldFocusState,
            // Close extended selector if text field receives focus
            onTextFieldFocused = { focused ->
                if (focused) {
                    currentInputSelector = InputSelector.TEXT
                    resetScroll()
                }
                textFieldFocusState = focused
            },
            onMessageSent = onMessageSent,
        )
        IconButton(modifier = Modifier.align(CenterVertically), onClick = {
            currentInputSelector = InputSelector.IMAGE
            addImage()
        }) {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = "cd_add_attachment"
            )
        }
        IconButton(modifier = Modifier.align(CenterVertically), onClick = {
            currentInputSelector = InputSelector.VOICE_MEMO
            recordMemo()
        }) {
            Icon(
                painter = painterResource(R.drawable.ic_mic),
                contentDescription = "cd_record_audio"
            )
        }
    }
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@ExperimentalFoundationApi
@Composable
private fun UserInputText(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    textFieldValue: TextFieldValue,
    keyboardShown: Boolean,
    onMessageSent: (String) -> Unit,
    onTextChanged: (TextFieldValue) -> Unit,
    onTextFieldFocused: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .semantics {
                keyboardShownProperty = keyboardShown
            },
    ) {
        var lastFocusState by remember { mutableStateOf(false) }
        TextField(
            value = textFieldValue.text,
            onValueChange = { onTextChanged(TextFieldValue(it)) },
            placeholder = {
                Text(stringResource(com.zero.android.feature.messages.R.string.write_your_message))
            },
            textStyle = Typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .onFocusChanged { state ->
                    if (lastFocusState != state.isFocused) {
                        onTextFieldFocused(state.isFocused)
                    }
                    lastFocusState = state.isFocused
                },
            shape = RoundedCornerShape(24.dp),
            colors =
            TextFieldDefaults.textFieldColors(
                textColor = AppTheme.colors.colorTextPrimary,
                disabledTextColor = AppTheme.colors.colorTextSecondary,
                focusedIndicatorColor = AppTheme.colors.colorTextSecondary,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = AppTheme.colors.colorTextSecondaryVariant,
                cursorColor = AppTheme.colors.colorTextPrimary,
                containerColor = Color(0xFF191919)
            ),
            keyboardOptions =
            KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onDone = { onMessageSent(textFieldValue.text) }
            )
        )
    }
}
