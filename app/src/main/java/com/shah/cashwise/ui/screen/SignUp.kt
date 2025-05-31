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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shah.cashwise.R
import com.shah.cashwise.data.model.auth.response.AuthUserResponse
import com.shah.cashwise.ui.components.auth.AuthMethodsSeparator
import com.shah.cashwise.ui.components.auth.SignUpHeader
import com.shah.cashwise.ui.components.common.PasswordTextField
import com.shah.cashwise.ui.components.common.PrimaryButton
import com.shah.cashwise.ui.components.common.PrimaryTextField
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.ui.viewmodel.AuthViewModel
import com.shah.cashwise.utils.ImageResource
import com.shah.cashwise.utils.ResponseResource
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Monil on 29/03/25.
 */

@Composable
fun SignUp(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val formState = viewModel.signUpFormState
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
        SignUpHeader()

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Your Name",
                error = formState.nameError,
                enabled = authState != ResponseResource.Loading,
                singleLine = true
            ) {
                viewModel.onNameChanged(it)
            }

            PrimaryTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Email Address",
                error = formState.emailError,
                enabled = authState != ResponseResource.Loading,
                singleLine = true
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
            if (authState != ResponseResource.Loading) "Sign Up" else "",
            enabled = authState != ResponseResource.Loading,
            isLoading = authState == ResponseResource.Loading,
            onClick = {
                Log.d("SignUp", "Sign up clicked")
                viewModel.validateAndRegister()
            }
        )

        Spacer(Modifier.height(20.dp))

        AuthMethodsSeparator(true)

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
            isLoading = false,
            onClick = {
                // Normally you'd get Google ID Token and call:
                // viewModel.signUpWithGoogle(idToken)
            }
        )
    }
}

@Preview
@Composable
fun PreviewSignUp() {
    CashWiseTheme {
        SignUp()
    }
}