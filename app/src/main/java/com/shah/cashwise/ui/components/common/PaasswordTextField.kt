package com.shah.cashwise.ui.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Created by Monil on 31/05/25.
 */

@Composable
fun PasswordTextField(
    error: String?,
    isEnabled: Boolean,
    focusManager: FocusManager,
    onPasswordChange: (String) -> Unit,
    onDoneClick: () -> Unit = {}
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val visualTransformation =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val image = if (isPasswordVisible)
        Icons.Default.Visibility
    else
        Icons.Default.VisibilityOff

    PrimaryTextField(
        modifier = Modifier.fillMaxWidth(),
        label = "Password",
        visualTransformation = visualTransformation,
        error = error,
        endIcon = image,
        enabled = isEnabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDoneClick()
            }
        ),
        onIconClick = {
            isPasswordVisible = !isPasswordVisible
        }
    ) {
        onPasswordChange(it)
    }

}
