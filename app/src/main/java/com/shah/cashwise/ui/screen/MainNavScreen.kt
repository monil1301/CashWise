package com.shah.cashwise.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.components.nav.AddTransactionButtons
import com.shah.cashwise.ui.components.nav.NavScreenBottomBar
import com.shah.cashwise.ui.components.nav.NavScreenHeader
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 07/06/25.
 */

@Composable
fun MainNavScreen() {
    var isAddOptionsOpen by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isAddOptionsOpen) -45f else 0f,
        label = "ButtonRotation"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = {
            NavScreenHeader("Home", "Monil Shah")
        },
        bottomBar = {
            NavScreenBottomBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isAddOptionsOpen = !isAddOptionsOpen
                },
                modifier = Modifier
                    .size(56.dp)
                    .offset(y = 45.dp)
                    .graphicsLayer {
                        rotationZ = rotationAngle
                    },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    Icons.Default.Add,
                    modifier = Modifier.size(36.dp),
                    contentDescription = "Add"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter).offset(y = (-20).dp),
                visible = isAddOptionsOpen,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = fadeOut()
            ) {
                AddTransactionButtons()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainNavScreen() {
    CashWiseTheme {
        MainNavScreen()
    }
}
