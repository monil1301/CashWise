package com.shah.cashwise

import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.sign_in_code_invalid
import cashwise.composeapp.generated.resources.sign_in_email_send_failed
import cashwise.composeapp.generated.resources.sign_in_error_generic
import cashwise.composeapp.generated.resources.sign_in_not_implemented
import com.shah.cashwise.domain.model.AuthSessionStatus
import com.shah.cashwise.domain.model.AuthUser
import com.shah.cashwise.domain.repo.AuthRepository
import com.shah.cashwise.ui.screens.signin.AuthProvider
import com.shah.cashwise.ui.screens.signin.SignInAction
import com.shah.cashwise.ui.screens.signin.SignInNavResult
import com.shah.cashwise.ui.screens.signin.SignInStep
import com.shah.cashwise.ui.screens.signin.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

private class FakeAuthRepository : AuthRepository {
    val status = MutableStateFlow<AuthSessionStatus>(AuthSessionStatus.SignedOut)
    var googleResult: Result<Unit> = Result.success(Unit)
    var sendResult: Result<Unit> = Result.success(Unit)
    var verifyResult: Result<Unit> = Result.success(Unit)
    var lastSentEmail: String? = null
    var signedOut = false

    override val sessionStatus: Flow<AuthSessionStatus> = status
    override suspend fun signInWithGoogle(): Result<Unit> = googleResult
    override suspend fun sendEmailOtp(email: String): Result<Unit> {
        lastSentEmail = email
        return sendResult
    }
    override suspend fun verifyEmailOtp(email: String, code: String): Result<Unit> = verifyResult
    override suspend fun signOut() {
        signedOut = true
        status.value = AuthSessionStatus.SignedOut
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun backOnProvidersReturnsExitToCaller() = runTest(dispatcher) {
        val viewModel = SignInViewModel(FakeAuthRepository())
        assertEquals(SignInNavResult.ExitToCaller, viewModel.onAction(SignInAction.Back))
    }

    @Test
    fun continueWithGoogleSetsLoadingProviderImmediately() = runTest(dispatcher) {
        val viewModel = SignInViewModel(FakeAuthRepository())

        viewModel.onAction(SignInAction.ContinueWithGoogle)

        assertEquals(AuthProvider.Google, viewModel.state.value.loadingProvider)
        assertNull(viewModel.state.value.errorMessage)
    }

    @Test
    fun googleFailureClearsLoadingAndSetsError() = runTest(dispatcher) {
        val repo = FakeAuthRepository().apply { googleResult = Result.failure(RuntimeException("boom")) }
        val viewModel = SignInViewModel(repo)

        viewModel.onAction(SignInAction.ContinueWithGoogle)
        advanceUntilIdle()

        assertNull(viewModel.state.value.loadingProvider)
        assertEquals(Res.string.sign_in_error_generic, viewModel.state.value.errorMessage)
    }

    /**
     * signInWithGoogle() returns once the browser is *launched*, not once sign-in
     * completes. The spinner must clear at that point — otherwise cancelling the
     * browser strands the screen in a permanent loading state with every button
     * disabled (isBusy), and the user can never retry.
     */
    @Test
    fun googleSpinnerClearsOnceBrowserLaunchedSoCancellingIsRecoverable() = runTest(dispatcher) {
        val viewModel = SignInViewModel(FakeAuthRepository())

        viewModel.onAction(SignInAction.ContinueWithGoogle)
        advanceUntilIdle()

        // Browser launched, user never came back with a session (i.e. cancelled).
        assertNull(viewModel.state.value.loadingProvider)
        assertFalse(viewModel.state.value.isBusy)
        assertNull(viewModel.state.value.errorMessage)
    }

    @Test
    fun sessionBecomingSignedInClearsInFlightSubmission() = runTest(dispatcher) {
        val repo = FakeAuthRepository()
        val viewModel = SignInViewModel(repo)

        repo.status.value = AuthSessionStatus.SignedIn(
            AuthUser(id = "u1", email = "a@b.co", displayName = null, avatarUrl = null),
        )
        advanceUntilIdle()

        assertNull(viewModel.state.value.loadingProvider)
        assertFalse(viewModel.state.value.isSubmitting)
    }

    @Test
    fun continueWithEmailOpensEmailStep() = runTest(dispatcher) {
        val viewModel = SignInViewModel(FakeAuthRepository())

        assertNull(viewModel.onAction(SignInAction.ContinueWithEmail))

        assertEquals(SignInStep.EmailEntry, viewModel.state.value.step)
    }

    @Test
    fun sendEmailCodeAdvancesToCodeStepOnSuccess() = runTest(dispatcher) {
        val repo = FakeAuthRepository()
        val viewModel = SignInViewModel(repo)

        viewModel.onAction(SignInAction.ContinueWithEmail)
        viewModel.onAction(SignInAction.EmailChanged("user@example.com"))
        assertTrue(viewModel.state.value.isEmailValid)

        viewModel.onAction(SignInAction.SendEmailCode)
        advanceUntilIdle()

        assertEquals("user@example.com", repo.lastSentEmail)
        assertEquals(SignInStep.CodeEntry, viewModel.state.value.step)
        assertNull(viewModel.state.value.errorMessage)
    }

    @Test
    fun sendEmailCodeFailureStaysOnEmailStepWithError() = runTest(dispatcher) {
        val repo = FakeAuthRepository().apply { sendResult = Result.failure(RuntimeException("smtp")) }
        val viewModel = SignInViewModel(repo)

        viewModel.onAction(SignInAction.ContinueWithEmail)
        viewModel.onAction(SignInAction.EmailChanged("user@example.com"))
        viewModel.onAction(SignInAction.SendEmailCode)
        advanceUntilIdle()

        assertEquals(SignInStep.EmailEntry, viewModel.state.value.step)
        assertEquals(Res.string.sign_in_email_send_failed, viewModel.state.value.errorMessage)
    }

    @Test
    fun codeChangedKeepsDigitsOnlyAndCaps() = runTest(dispatcher) {
        val viewModel = SignInViewModel(FakeAuthRepository())

        viewModel.onAction(SignInAction.CodeChanged("12ab34cd567"))

        assertEquals("123456", viewModel.state.value.code)
        assertTrue(viewModel.state.value.isCodeValid)
    }

    @Test
    fun verifyCodeFailureShowsInvalidError() = runTest(dispatcher) {
        val repo = FakeAuthRepository().apply { verifyResult = Result.failure(RuntimeException("bad")) }
        val viewModel = SignInViewModel(repo)

        viewModel.onAction(SignInAction.ContinueWithEmail)
        viewModel.onAction(SignInAction.EmailChanged("user@example.com"))
        viewModel.onAction(SignInAction.SendEmailCode)
        advanceUntilIdle()
        viewModel.onAction(SignInAction.CodeChanged("123456"))
        viewModel.onAction(SignInAction.VerifyCode)
        advanceUntilIdle()

        assertEquals(Res.string.sign_in_code_invalid, viewModel.state.value.errorMessage)
        assertNull(viewModel.state.value.loadingProvider)
    }

    @Test
    fun backStepsFromCodeToEmailToProviders() = runTest(dispatcher) {
        val repo = FakeAuthRepository()
        val viewModel = SignInViewModel(repo)

        viewModel.onAction(SignInAction.ContinueWithEmail)
        viewModel.onAction(SignInAction.EmailChanged("user@example.com"))
        viewModel.onAction(SignInAction.SendEmailCode)
        advanceUntilIdle()
        assertEquals(SignInStep.CodeEntry, viewModel.state.value.step)

        assertNull(viewModel.onAction(SignInAction.Back))
        assertEquals(SignInStep.EmailEntry, viewModel.state.value.step)

        assertNull(viewModel.onAction(SignInAction.Back))
        assertEquals(SignInStep.Providers, viewModel.state.value.step)

        assertEquals(SignInNavResult.ExitToCaller, viewModel.onAction(SignInAction.Back))
    }

    @Test
    fun phoneActionShowsNotImplementedAndDismissClears() = runTest(dispatcher) {
        val viewModel = SignInViewModel(FakeAuthRepository())

        viewModel.onAction(SignInAction.UsePhoneOtp)
        assertEquals(Res.string.sign_in_not_implemented, viewModel.state.value.errorMessage)

        viewModel.onAction(SignInAction.DismissError)
        assertNull(viewModel.state.value.errorMessage)
    }
}
