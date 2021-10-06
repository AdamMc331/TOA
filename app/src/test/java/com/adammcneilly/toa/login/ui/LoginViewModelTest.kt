package com.adammcneilly.toa.login.ui

import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.Email
import com.adammcneilly.toa.login.domain.model.Password
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private lateinit var testRobot: LoginViewModelRobot

    private val defaultCredentials = Credentials(
        Email("testy@mctestface.com"),
        Password("Hunter2"),
    )

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
            .assertViewStatesAfterAction(
                action = {
                    this.enterEmail(testEmail)
                    this.enterPassword(testPassword)
                },
                viewStates = expectedViewStates,
            )
    }
}
