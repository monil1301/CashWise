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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.data.model.auth.FirebaseUserSignUp
import com.shah.cashwise.data.model.auth.response.AuthUserResponse
import com.shah.cashwise.ui.components.auth.AuthMethodsSeparator
import com.shah.cashwise.ui.components.auth.SignUpHeader
import com.shah.cashwise.ui.components.common.PrimaryButton
import com.shah.cashwise.ui.components.common.PrimaryTextField
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.utils.ImageResource
import com.shah.cashwise.utils.ResponseResource
import com.shah.cashwise.utils.extensions.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Monil on 29/03/25.
 */

@Composable
fun SignUp(
    authState: StateFlow<ResponseResource<AuthUserResponse>?>,
    onGoogleLogin: () -> Unit,
    onSignUp: (user: FirebaseUserSignUp) -> Unit
) {
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state = authState.collectAsState()

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
                error = nameError,
                enabled = state.value != ResponseResource.Loading
            ) {
                name = it
                nameError = null
            }

            PrimaryTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Email Address",
                error = emailError,
                enabled = state.value != ResponseResource.Loading
            ) {
                email = it
                emailError = null
            }

            PrimaryTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Password",
                error = passwordError,
                enabled = state.value != ResponseResource.Loading
            ) {
                password = it
                passwordError = null
            }
        }

        Spacer(Modifier.height(16.dp))

        PrimaryButton(
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            if (state.value != ResponseResource.Loading) "Sign Up" else "",
            enabled = state.value != ResponseResource.Loading,
            isLoading = state.value == ResponseResource.Loading,
            onClick = {
                if (name.isBlank() || name.length < 2)
                    nameError = "Name should be at least 2 characters"

                if (!email.isValidEmail())
                    emailError = "Email is invalid"

                if (password.isBlank() || password.length < 6)
                    passwordError = "Password should be at least 6 characters"

                if (nameError == null && emailError == null && passwordError == null) {
                    onSignUp(FirebaseUserSignUp(name, email, password))
                    Log.d("SignUp", "Sign up clicked")
                }
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
                onGoogleLogin()
            }
        )
    }
}

@Preview
@Composable
fun PreviewSignUp() {
    val dummyState = MutableStateFlow<ResponseResource<AuthUserResponse>?>(ResponseResource.Failure())
    CashWiseTheme {
        SignUp(
            dummyState, {}
        ) {}
    }
}