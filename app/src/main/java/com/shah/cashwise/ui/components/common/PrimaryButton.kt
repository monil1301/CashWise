package com.shah.cashwise.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.ui.uitils.enums.ViewPosition
import com.shah.cashwise.utils.ImageResource

/**
 * Created by Monil on 02/05/25.
 */

@Composable
fun PrimaryButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    imageResource: ImageResource = ImageResource.None,
    textStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    iconPosition: ViewPosition = ViewPosition.START,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        shape = RoundedCornerShape(20),
        contentPadding = contentPadding
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (!isLoading) {
                if (iconPosition == ViewPosition.START)
                    ShowImageFromResource(imageResource,
                        Modifier
                            .size(50.dp)
                            .padding(end = 10.dp))

                Text(text, style = textStyle)

                if (iconPosition == ViewPosition.END)
                    ShowImageFromResource(
                        imageResource,
                        Modifier
                            .size(50.dp)
                            .padding(start = 10.dp)
                    )
            } else {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun ShowImageFromResource(imageResource: ImageResource, modifier: Modifier) {
    when (imageResource) {
        is ImageResource.IconResource -> {
            Image(
                imageResource.icon,
                imageResource.contentDescription,
                modifier = modifier
            )
        }

        is ImageResource.PainterResource -> {
            Image(
                imageResource.painter,
                imageResource.contentDescription,
                modifier = modifier
            )
        }

        ImageResource.None -> {}
    }
}

@Preview
@Composable
fun PreviewPrimaryButton() {
    CashWiseTheme {
        PrimaryButton(
            Modifier,
            "Sign Up",
            imageResource = ImageResource.PainterResource(
                painterResource(R.drawable.google_icons_09_512),
                ""
            ),
            onClick = {},
            isLoading = false
        )
    }
}
