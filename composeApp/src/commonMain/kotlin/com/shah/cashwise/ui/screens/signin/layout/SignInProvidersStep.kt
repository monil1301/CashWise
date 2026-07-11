package com.shah.cashwise.ui.screens.signin.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.ic_logo_google
import cashwise.composeapp.generated.resources.sign_in_continue_email
import cashwise.composeapp.generated.resources.sign_in_continue_google
import cashwise.composeapp.generated.resources.sign_in_footer_hint
import cashwise.composeapp.generated.resources.sign_in_or
import cashwise.composeapp.generated.resources.sign_in_signing_in
import cashwise.composeapp.generated.resources.sign_in_use_phone_otp
import com.shah.cashwise.ui.screens.signin.AuthProvider
import com.shah.cashwise.ui.screens.signin.SignInAction
import com.shah.cashwise.ui.screens.signin.SignInState
import com.shah.cashwise.ui.screens.signin.components.SignInProviderButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val HorizontalGutter = 24.dp

/**
 * The provider list step — top bar, the sync info banner, the provider buttons
 * (Google / Email) split by an "or" divider, an inline error/notice, then the
 * phone-OTP fallback pinned near the bottom.
 */
@Composable
internal fun SignInProvidersStep(
    state: SignInState,
    onAction: (SignInAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val googleLoading = state.loadingProvider == AuthProvider.Google

    Column(modifier = modifier.fillMaxSize()) {
        SignInTopBar(
            onBack = { onAction(SignInAction.Back) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter, vertical = 8.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            SignInSyncBanner(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            // Google — outlined surface; keep the multi-colour mark via Image,
            // swapping in a spinner while the browser flow is in progress.
            SignInProviderButton(
                text = stringResource(
                    if (googleLoading) Res.string.sign_in_signing_in else Res.string.sign_in_continue_google,
                ),
                onClick = { onAction(SignInAction.ContinueWithGoogle) },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                enabled = !state.isBusy,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    if (googleLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    } else {
                        Image(
                            painter = painterResource(Res.drawable.ic_logo_google),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            OrDivider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(20.dp))

            // Email — outlined, brand-tinted; opens the email-OTP step.
            SignInProviderButton(
                text = stringResource(Res.string.sign_in_continue_email),
                onClick = { onAction(SignInAction.ContinueWithEmail) },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                enabled = !state.isBusy,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )

            state.errorMessage?.let { message ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(message),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        SignInFooter(
            enabled = !state.isBusy,
            onUsePhoneOtp = { onAction(SignInAction.UsePhoneOtp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/** A centred "or" label flanked by hairline rules, separating the social and email paths. */
@Composable
private fun OrDivider(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = stringResource(Res.string.sign_in_or),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

/** Phone-OTP fallback link plus the reassurance caption at the foot of the screen. */
@Composable
private fun SignInFooter(
    enabled: Boolean,
    onUsePhoneOtp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextButton(onClick = onUsePhoneOtp, enabled = enabled) {
            Text(
                text = stringResource(Res.string.sign_in_use_phone_otp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(Res.string.sign_in_footer_hint),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
