package com.shah.cashwise.ui.components.pin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 01/06/25.
 */

@Composable
fun NumericPad(
    onDeleteClick: () -> Unit,
    onNumberClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            NumericButton(
                modifier = Modifier.weight(1f),
                number = 1,
                onClick = {
                    onNumberClick(1)
                }
            )

            NumericButton(
                modifier = Modifier.weight(1f),
                number = 2,
                onClick = {
                    onNumberClick(2)
                }
            )

            NumericButton(
                modifier = Modifier.weight(1f),
                number = 3,
                onClick = {
                    onNumberClick(3)
                }
            )
        }

        Row {
            NumericButton(
                modifier = Modifier.weight(1f),
                number = 4,
                onClick = {
                    onNumberClick(4)
                }
            )

            NumericButton(
                modifier = Modifier.weight(1f),
                number = 5,
                onClick = {
                    onNumberClick(5)
                }
            )

            NumericButton(
                modifier = Modifier.weight(1f),
                number = 6,
                onClick = {
                    onNumberClick(6)
                }
            )
        }

        Row {
            NumericButton(
                modifier = Modifier.weight(1f),
                number = 7,
                onClick = {
                    onNumberClick(7)
                }
            )

            NumericButton(
                modifier = Modifier.weight(1f),
                number = 8,
                onClick = {
                    onNumberClick(8)
                }
            )

            NumericButton(
                modifier = Modifier.weight(1f),
                number = 9,
                onClick = {
                    onNumberClick(9)
                }
            )
        }

        Row {
            NumericButton(
                modifier = Modifier.weight(1f),
                number = 0,
                buttonType = NumericPadButtonType.BACK,
                onClick = onDeleteClick
            )

            NumericButton(
                modifier = Modifier.weight(1f),
                number = 0,
                onClick = {
                    onNumberClick(0)
                }
            )

            NumericButton(
                modifier = Modifier.weight(1f),
                number = 0,
                buttonType = NumericPadButtonType.NEXT,
                onClick = {}
            )
        }
    }
}

enum class NumericPadButtonType {
    NUMBER, NEXT, BACK
}

@Composable
fun NumericButton(
    modifier: Modifier,
    number: Int,
    buttonType: NumericPadButtonType = NumericPadButtonType.NUMBER,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        if (buttonType != NumericPadButtonType.NUMBER)
            Icon(
                if(buttonType == NumericPadButtonType.NEXT) Icons.AutoMirrored.Filled.ArrowForward else Icons.AutoMirrored.Filled.Backspace,
                "",
                Modifier.size(44.dp)
            )
        else
            Text(
                number.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                fontSize = 42.sp
            )
    }
}

@Preview
@Composable
fun PreviewNumericPad() {
    CashWiseTheme {
        NumericPad ({}) {}
    }
}
