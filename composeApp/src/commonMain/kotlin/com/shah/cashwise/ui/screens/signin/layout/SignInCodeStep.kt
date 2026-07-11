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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.sign_in_code_heading
import cashwise.composeapp.generated.resources.sign_in_code_label
import cashwise.composeapp.generated.resources.sign_in_code_resend
import cashwise.composeapp.generated.resources.sign_in_code_subtitle
import cashwise.composeapp.generated.resources.sign_in_code_verify
import com.shah.cashwise.ui.components.LabeledField
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.signin.SignInAction
import com.shah.cashwise.ui.screens.signin.SignInState
import org.jetbrains.compose.resources.stringResource

private val HorizontalGutter = 24.dp

/** Code-entry step — verify the 6-digit code emailed to [SignInState.email]. */
@Composable
internal fun SignInCodeStep(
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
                text = stringResource(Res.string.sign_in_code_heading),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.sign_in_code_subtitle, state.email),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(24.dp))

            LabeledField(
                label = stringResource(Res.string.sign_in_code_label),
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = state.code,
                    onValueChange = { onAction(SignInAction.CodeChanged(it)) },
                    singleLine = true,
                    enabled = !state.isSubmitting,
                    isError = state.errorMessage != null,
                    shape = MaterialTheme.shapes.large,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { onAction(SignInAction.VerifyCode) }),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            SignInStepError(message = state.errorMessage)

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = stringResource(Res.string.sign_in_code_verify),
                onClick = { onAction(SignInAction.VerifyCode) },
                enabled = state.isCodeValid && !state.isBusy,
                modifier = Modifier.fillMaxWidth(),
            )

            SignInStepProgress(visible = state.isSubmitting)

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { onAction(SignInAction.ResendCode) },
                enabled = !state.isBusy,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = stringResource(Res.string.sign_in_code_resend),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
