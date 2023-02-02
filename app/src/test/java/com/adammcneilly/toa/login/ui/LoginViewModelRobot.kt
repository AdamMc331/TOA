package com.adammcneilly.toa.login.ui

import app.cash.turbine.test
import com.adammcneilly.toa.fakes.FakeLoginRepository
import com.adammcneilly.toa.fakes.FakeTokenRepository
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.LoginResponse
import com.adammcneilly.toa.login.domain.usecase.ProdCredentialsLoginUseCase
import com.google.common.truth.Truth.assertThat

class LoginViewModelRobot {
    private val fakeLoginRepository = FakeLoginRepository()
    private val fakeTokenRepository = FakeTokenRepository()

    private val credentialsLoginUseCase = ProdCredentialsLoginUseCase(
        loginRepository = fakeLoginRepository.mock,
        tokenRepository = fakeTokenRepository.mock,
    )

    private lateinit var viewModel: LoginViewModel

    fun buildViewModel() = apply {
        viewModel = LoginViewModel(
            credentialsLoginUseCase = credentialsLoginUseCase,
        )
    }

    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: Result<LoginResponse>,
    ) = apply {
        fakeLoginRepository.mockLoginWithCredentials(
            credentials,
            result,
        )
    }

    fun enterEmail(email: String) = apply {
        viewModel.emailChanged(email)
    }

    fun enterPassword(password: String) = apply {
        viewModel.passwordChanged(password)
    }

    fun clickLogInButton() = apply {
        viewModel.loginButtonClicked()
    }

    fun clickSignUpButton() = apply {
        viewModel.signUpButtonClicked()
    }

    /**
     * Launch a coroutine that will observe our [viewModel]'s view state and ensure that we consume
     * all of the supplied [viewStates] in the same order that they are supplied.
     *
     * We should call this near the front of the test, to ensure that every view state we emit
     * can be collected by this.
     */
    suspend fun expectViewStates(
        action: LoginViewModelRobot.() -> Unit,
        viewStates: List<LoginViewState>,
    ) = apply {
        viewModel.viewState.test {
            action()

            for (state in viewStates) {
                assertThat(awaitItem()).isEqualTo(state)
            }

            this.cancel()
        }
    }
}
