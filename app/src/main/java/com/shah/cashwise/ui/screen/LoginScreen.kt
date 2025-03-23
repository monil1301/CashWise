package com.shah.cashwise.ui.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.shah.cashwise.ui.viewmodel.AuthViewModel
import com.shah.cashwise.utils.AuthResult

@Composable
fun LoginScreen(viewModel: AuthViewModel = hiltViewModel()) {

    val authState by viewModel.authState.collectAsState()

    Button(
        onClick = {
            viewModel.registerUser("abc@gmail.com", "12345678")
        }
    ) {
        Text(
            text = when (authState) {
                is AuthResult.Success -> "Success"
                is AuthResult.Failure -> "Failed"
                is AuthResult.Loading -> "Loading..."
                else -> "Proceed"
            }
        )
    }
}