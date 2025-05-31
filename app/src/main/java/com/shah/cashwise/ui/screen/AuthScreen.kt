package com.shah.cashwise.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shah.cashwise.ui.components.auth.SwitchAuthTypeHeader
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.ui.viewmodel.AuthViewModel
import com.shah.cashwise.utils.ResponseResource
import kotlinx.coroutines.launch

/**
 * Created by Monil on 29/03/25.
 */

@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val state = viewModel.authState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Show snack bar on failure
    LaunchedEffect(state.value) {
        if (state.value is ResponseResource.Failure) {
            val message = (state.value as ResponseResource.Failure).errorMessage
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.inversePrimary,
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onPrimary,
                        )
                    )
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SwitchAuthTypeHeader(viewModel.isRegistering) {
                viewModel.isRegistering = !viewModel.isRegistering
            }

            Text(
                "CashWise",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.displayMedium
            )

            if (viewModel.isRegistering)
                SignUp()
            else
                LoginScreen()
        }
    }
}

@Preview
@Composable
fun PreviewAuthScreen() {
    CashWiseTheme(darkTheme = false) {
        AuthScreen()
    }
}