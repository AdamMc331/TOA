package com.adammcneilly.toa.login.ui

import com.adammcneilly.toa.fakes.FakeCredentialsLoginUseCase
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.LoginResult
import com.google.common.truth.Truth.assertThat

class LoginViewModelRobot {
    private val fakeCredentialsLoginUseCase = FakeCredentialsLoginUseCase()

    private lateinit var viewModel: LoginViewModel

    fun buildViewModel() = apply {
        viewModel = LoginViewModel()
    }

    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: LoginResult,
    ) = apply {
        fakeCredentialsLoginUseCase.mockLoginResultForCredentials(credentials, result)
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

    fun assertViewState(expectedViewState: LoginViewState) = apply {
        assertThat(viewModel.viewState.value).isEqualTo(expectedViewState)
    }
}
