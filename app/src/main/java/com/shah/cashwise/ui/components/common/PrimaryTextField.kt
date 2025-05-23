package com.shah.cashwise.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Created by Monil on 01/05/25.
 */

@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    label: String,
    defaultValue: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    error: String? = null,
    onValueChange: (String) -> Unit
) {
    val textValue = remember { mutableStateOf(defaultValue) }
    Column {
        OutlinedTextField(
            textValue.value,
            modifier = modifier,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            isError = isError,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(20),
            label = {
                Text(label)
            },
            onValueChange = { value ->
                textValue.value = value

                onValueChange(value)
            })

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewOutlinedTextField() {
    PrimaryTextField(label = "your name", error = "cannot be empty") {}
}