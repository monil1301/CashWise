package com.shah.cashwise.ui.screens.signin.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.sign_in_email_heading
import cashwise.composeapp.generated.resources.sign_in_email_label
import cashwise.composeapp.generated.resources.sign_in_email_placeholder
import cashwise.composeapp.generated.resources.sign_in_email_send
import cashwise.composeapp.generated.resources.sign_in_email_subtitle
import com.shah.cashwise.ui.components.LabeledField
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.signin.SignInAction
import com.shah.cashwise.ui.screens.signin.SignInState
import org.jetbrains.compose.resources.stringResource

private val HorizontalGutter = 24.dp

/** Email-entry step — collect the address, then send a 6-digit code to it. */
@Composable
internal fun SignInEmailStep(
    state: SignInState,
    onAction: (SignInAction) -> Unit,
    modifier: Modifier = Modifier,
) {
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

            Text(
                text = stringResource(Res.string.sign_in_email_heading),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.sign_in_email_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(24.dp))

            LabeledField(
                label = stringResource(Res.string.sign_in_email_label),
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onAction(SignInAction.EmailChanged(it)) },
                    placeholder = { Text(stringResource(Res.string.sign_in_email_placeholder)) },
                    singleLine = true,
                    enabled = !state.isSubmitting,
                    isError = state.errorMessage != null,
                    shape = MaterialTheme.shapes.large,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { onAction(SignInAction.SendEmailCode) }),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            SignInStepError(message = state.errorMessage)

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = stringResource(Res.string.sign_in_email_send),
                onClick = { onAction(SignInAction.SendEmailCode) },
                enabled = state.isEmailValid && !state.isBusy,
                modifier = Modifier.fillMaxWidth(),
            )

            SignInStepProgress(visible = state.isSubmitting)
        }
    }
}
