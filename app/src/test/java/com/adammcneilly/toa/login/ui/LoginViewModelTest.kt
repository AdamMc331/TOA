package com.adammcneilly.toa.login.ui

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.R
import com.adammcneilly.toa.ThreadExceptionHandlerTestRule
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.Email
import com.adammcneilly.toa.login.domain.model.InvalidCredentialsException
import com.adammcneilly.toa.login.domain.model.Password
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    private lateinit var testRobot: LoginViewModelRobot

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val threadExceptionHandlerTestRule = ThreadExceptionHandlerTestRule()

    @Before
    fun setUp() {
        testRobot = LoginViewModelRobot()
    }

    @Test
    fun testUpdateCredentials() = runBlockingTest {
        val testEmail = "testy@mctestface.com"
        val testPassword = "Hunter2"

        val initialState = LoginViewState.Initial
        val emailEnteredState = LoginViewState.Active(
            credentials = Credentials(email = Email(testEmail))
        )
        val emailPasswordEnteredState = LoginViewState.Active(
            credentials = Credentials(email = Email(testEmail), password = Password(testPassword))
        )

        val expectedViewStates = listOf(
            initialState,
            emailEnteredState,
            emailPasswordEnteredState,
        )

        testRobot
            .buildViewModel()
            .expectViewStates(
                action = {
                    enterEmail(testEmail)
                    enterPassword(testPassword)
                },
                viewStates = expectedViewStates,
            )
    }

    @Test
    fun testSubmitInvalidCredentials() = runBlockingTest {
        val testEmail = "testy@mctestface.com"
        val testPassword = "Hunter2"
        val completedCredentials = Credentials(
            email = Email(testEmail),
            password = Password(testPassword),
        )

        val initialState = LoginViewState.Initial
        val emailEnteredState = LoginViewState.Active(
            credentials = Credentials(email = Email(testEmail))
        )
        val emailPasswordEnteredState = LoginViewState.Active(
            credentials = completedCredentials,
        )
        val submittingState = LoginViewState.Submitting(
            credentials = completedCredentials,
        )
        val submissionErrorState = LoginViewState.SubmissionError(
            credentials = completedCredentials,
            errorMessage = UIText.ResourceText(R.string.err_invalid_credentials)
        )

        val expectedViewStates = listOf(
            initialState,
            emailEnteredState,
            emailPasswordEnteredState,
            submittingState,
            submissionErrorState,
        )

        testRobot
            .buildViewModel()
            .mockLoginResultForCredentials(
                credentials = completedCredentials,
                result = Result.failure(InvalidCredentialsException()),
            )
            .expectViewStates(
                action = {
                    enterEmail(testEmail)
                    enterPassword(testPassword)
                    clickLogInButton()
                },
                viewStates = expectedViewStates,
            )
    }

    @Test
    fun testUnknownLoginFailure() = runBlockingTest {
        val testEmail = "testy@mctestface.com"
        val testPassword = "Hunter2"
        val completedCredentials = Credentials(
            email = Email(testEmail),
            password = Password(testPassword),
        )

        val initialState = LoginViewState.Initial
        val emailEnteredState = LoginViewState.Active(
            credentials = Credentials(email = Email(testEmail))
        )
        val emailPasswordEnteredState = LoginViewState.Active(
            credentials = completedCredentials,
        )
        val submittingState = LoginViewState.Submitting(
            credentials = completedCredentials,
        )
        val submissionErrorState = LoginViewState.SubmissionError(
            credentials = completedCredentials,
            errorMessage = UIText.ResourceText(R.string.err_login_failure),
        )

        val expectedViewStates = listOf(
            initialState,
            emailEnteredState,
            emailPasswordEnteredState,
            submittingState,
            submissionErrorState,
        )

        testRobot
            .buildViewModel()
            .mockLoginResultForCredentials(
                credentials = completedCredentials,
                result = Result.failure(
                    Throwable("Failed."),
                ),
            )
            .expectViewStates(
                action = {
                    enterEmail(testEmail)
                    enterPassword(testPassword)
                    clickLogInButton()
                },
                viewStates = expectedViewStates,
            )
    }

    @Test
    fun testSubmitWithoutCredentials() = runBlockingTest {
        val credentials = Credentials()

        val initialState = LoginViewState.Initial
        val submittingState = LoginViewState.Submitting(
            credentials = credentials,
        )
        val invalidInputsState = LoginViewState.Active(
            credentials = credentials,
            emailInputErrorMessage = UIText.ResourceText(R.string.err_empty_email),
            passwordInputErrorMessage = UIText.ResourceText(R.string.err_empty_password),
        )

        testRobot
            .buildViewModel()
            .expectViewStates(
                action = {
                    clickLogInButton()
                },
                viewStates = listOf(initialState, submittingState, invalidInputsState),
            )
    }

    @Test
    fun testClearErrorsAfterInput() = runBlockingTest {
        val credentials = Credentials()
        val testEmail = "testy@mctestface.com"
        val testPassword = "Hunter2"

        val initialState = LoginViewState.Initial
        val submittingState = LoginViewState.Submitting(
            credentials = credentials,
        )
        val invalidInputsState = LoginViewState.Active(
            credentials = credentials,
            emailInputErrorMessage = UIText.ResourceText(R.string.err_empty_email),
            passwordInputErrorMessage = UIText.ResourceText(R.string.err_empty_password),
        )
        val emailInputState = LoginViewState.Active(
            credentials = Credentials(email = Email(testEmail)),
            emailInputErrorMessage = null,
            passwordInputErrorMessage = UIText.ResourceText(R.string.err_empty_password),
        )
        val passwordInputState = LoginViewState.Active(
            credentials = Credentials(email = Email(testEmail), password = Password(testPassword)),
            emailInputErrorMessage = null,
            passwordInputErrorMessage = null,
        )

        testRobot
            .buildViewModel()
            .expectViewStates(
                action = {
                    clickLogInButton()
                    enterEmail(testEmail)
                    enterPassword(testPassword)
                },
                viewStates = listOf(
                    initialState,
                    submittingState,
                    invalidInputsState,
                    emailInputState,
                    passwordInputState,
                ),
            )
    }
}
