package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.invite_qr_caption
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrErrorCorrectionLevel
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import org.jetbrains.compose.resources.stringResource

/**
 * Outlined card displaying the QR code for [link] with the "Scan to join this
 * wallet" caption beneath. Renders via [rememberQrCodePainter] with the brand
 * styling from the mock — green circular dots, rounded finder frames, and a
 * centred wallet badge — at High error correction so it stays scannable.
 *
 * The card is square (1:1) and lets its parent decide the width.
 */
@Composable
internal fun InviteQrCard(
    link: String,
    modifier: Modifier = Modifier,
) {
    val darkColor = MaterialTheme.colorScheme.primary
    val painter = rememberQrCodePainter(link) {
        errorCorrectionLevel = QrErrorCorrectionLevel.High
        shapes {
            darkPixel = QrPixelShape.circle()
            ball = QrBallShape.circle()
            frame = QrFrameShape.roundCorners(.25f)
        }
        colors {
            dark = QrBrush.solid(darkColor)
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
                QrBrandBadge(modifier = Modifier.fillMaxSize(0.22f))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(Res.string.invite_qr_caption),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}
