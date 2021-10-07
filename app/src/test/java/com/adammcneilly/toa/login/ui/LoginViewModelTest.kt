package com.adammcneilly.toa.login.ui

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.R
import com.adammcneilly.toa.ThreadExceptionHandlerTestRule
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.Email
import com.adammcneilly.toa.login.domain.model.LoginResult
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
                viewStates = expectedViewStates,
            )
            .enterEmail(testEmail)
            .enterPassword(testPassword)
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
                result = LoginResult.Failure.InvalidCredentials,
            )
            .expectViewStates(
                viewStates = expectedViewStates,
            )
            .enterEmail(testEmail)
            .enterPassword(testPassword)
            .clickLogInButton()
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
                result = LoginResult.Failure.Unknown,
            )
            .expectViewStates(
                viewStates = expectedViewStates,
            )
            .enterEmail(testEmail)
            .enterPassword(testPassword)
            .clickLogInButton()
    }

    @Test
    fun testSubmitWithoutCredentials() = runBlockingTest {
        val initialState = LoginViewState.Initial
        val invalidInputsState = LoginViewState.Active(
            credentials = Credentials(),
            emailInputErrorMessage = UIText.ResourceText(R.string.err_empty_email),
            passwordInputErrorMessage = UIText.ResourceText(R.string.err_empty_password),
        )

        testRobot
            .buildViewModel()
            .expectViewStates(
                viewStates = listOf(initialState, invalidInputsState)
            )
            .clickLogInButton()
    }
}
