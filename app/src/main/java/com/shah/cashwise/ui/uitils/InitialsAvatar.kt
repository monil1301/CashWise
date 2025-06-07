package com.shah.cashwise.ui.uitils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shah.cashwise.ui.theme.letterColorMap
import com.shah.cashwise.utils.extensions.getInitials

/**
 * Created by Monil on 25/05/25.
 */

@Composable
fun InitialsAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    fontSize: TextUnit = 20.sp,
    shape: Shape = RoundedCornerShape(20)
) {
    val initials = name.getInitials()
    val firstChar = initials.firstOrNull()?.uppercaseChar()

    val bgColor = if (firstChar != null && firstChar in 'A'..'Z') {
        letterColorMap[firstChar] ?: Color.Gray
    } else {
        Color.Gray
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
    }
}
