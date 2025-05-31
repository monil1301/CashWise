package com.shah.cashwise.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shah.cashwise.R
import com.shah.cashwise.ui.components.auth.AuthMethodsSeparator
import com.shah.cashwise.ui.components.auth.LoginHeader
import com.shah.cashwise.ui.components.common.PasswordTextField
import com.shah.cashwise.ui.components.common.PrimaryButton
import com.shah.cashwise.ui.components.common.PrimaryTextField
import com.shah.cashwise.ui.viewmodel.AuthViewModel
import com.shah.cashwise.utils.ImageResource
import com.shah.cashwise.utils.ResponseResource

/**
 * Created by Monil on 29/03/25.
 */

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val formState = viewModel.loginFormState
    val authState = viewModel.authState.collectAsState().value
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(650.dp)
            .background(
                shape = RoundedCornerShape(7),
                color = MaterialTheme.colorScheme.background
            )
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {
        LoginHeader()

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Email Address",
                error = formState.emailError,
                enabled = authState != ResponseResource.Loading
            ) {
                viewModel.onEmailChanged(it)
            }

            PasswordTextField(
                error = formState.passwordError,
                isEnabled = authState != ResponseResource.Loading,
                focusManager = focusManager,
                onPasswordChange = {
                    viewModel.onPasswordChanged(it)
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        PrimaryButton(
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            if (authState != ResponseResource.Loading) "Login" else "",
            enabled = authState != ResponseResource.Loading,
            onClick = {
                Log.d("Login", "Login clicked")
                viewModel.validateAndLogin()
            }
        )

        Spacer(Modifier.height(20.dp))

        Text("Forgot Password?", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

        Spacer(Modifier.height(20.dp))

        AuthMethodsSeparator(false)

        Spacer(Modifier.height(20.dp))

        PrimaryButton(
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            "Google",
            imageResource = ImageResource.PainterResource(
                painterResource(R.drawable.google_icons_09_512),
                "Google"
            ),
            onClick = {
//                onGoogleLogin()
            }
        )

        Spacer(Modifier.height(35.dp))
    }
}

