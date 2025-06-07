package com.shah.cashwise.ui.components.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.ui.uitils.BottomBarWithNotchShape
import com.shah.cashwise.ui.uitils.enums.MainNavBarScreen

/**
 * Created by Monil on 26/05/25.
 */


@Composable
fun NavScreenBottomBar() {
    var currentScreen by remember { mutableStateOf(MainNavBarScreen.HOME) }

    BottomAppBar(
        modifier = Modifier
            .clip(BottomBarWithNotchShape())
            .height(80.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 6.dp,
        contentPadding = PaddingValues(horizontal = 12.dp),
        actions = {
            BottomNavBarItem(
                painter = painterResource(R.drawable.home),
                contentDescription = "Home",
                isSelected = currentScreen == MainNavBarScreen.HOME
            ) {
                currentScreen = MainNavBarScreen.HOME
            }

            Spacer(Modifier.width(30.dp))

            BottomNavBarItem(
                painter = painterResource(R.drawable.transaction),
                contentDescription = "Transfer",
                isSelected = currentScreen == MainNavBarScreen.TRANSACTIONS
            ) {
                currentScreen = MainNavBarScreen.TRANSACTIONS
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes next icons to the right

            BottomNavBarItem(
                painter = painterResource(R.drawable.pie_chart),
                contentDescription = "Pie Chart",
                isSelected = currentScreen == MainNavBarScreen.BUDGET
            ) {
                currentScreen = MainNavBarScreen.BUDGET
            }

            Spacer(Modifier.width(30.dp))

            BottomNavBarItem(
                painter = painterResource(R.drawable.settings),
                contentDescription = "Settings",
                isSelected = currentScreen == MainNavBarScreen.SETTINGS
            ) {
                currentScreen = MainNavBarScreen.SETTINGS
            }
        }
    )
}

@Preview
@Composable
fun PreviewNavScreenBottomBar() {
    CashWiseTheme {
        NavScreenBottomBar()
    }
}
