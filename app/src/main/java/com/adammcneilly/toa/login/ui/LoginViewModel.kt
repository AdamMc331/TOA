package com.adammcneilly.toa.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.Email
import com.adammcneilly.toa.login.domain.model.LoginResult
import com.adammcneilly.toa.login.domain.model.Password
import com.adammcneilly.toa.login.domain.usecase.CredentialsLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val credentialsLoginUseCase: CredentialsLoginUseCase,
) : ViewModel() {

    private val _viewState: MutableStateFlow<LoginViewState> =
        MutableStateFlow(LoginViewState.Initial)
    val viewState: StateFlow<LoginViewState> = _viewState

    fun emailChanged(email: String) {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Active(
            credentials = currentCredentials.withUpdatedEmail(email),
        )
    }

    fun passwordChanged(password: String) {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Active(
            credentials = currentCredentials.withUpdatedPassword(password)
        )
    }

    fun loginButtonClicked() {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Submitting(
            credentials = currentCredentials,
        )

        viewModelScope.launch {
            val loginResult = credentialsLoginUseCase(currentCredentials)

            _viewState.value = when (loginResult) {
                is LoginResult.Failure.InvalidCredentials -> {
                    LoginViewState.SubmissionError(
                        credentials = currentCredentials,
                        errorMessage = UIText.ResourceText(R.string.err_invalid_credentials),
                    )
                }
                is LoginResult.Failure.Unknown -> {
                    LoginViewState.SubmissionError(
                        credentials = currentCredentials,
                        errorMessage = UIText.ResourceText(R.string.err_login_failure),
                    )
                }
                else -> _viewState.value
            }
        }
    }

    fun signUpButtonClicked() {
        TODO()
    }

    /**
     * Given some [credentials], ensure that we've been provided valid information that can be used
     * to login. If not, update our state accordingly, and return whether or not to proceed.
     */
    private fun validateCredentials(credentials: Credentials): Boolean {
        val hasEmail = credentials.email.value.isNotEmpty()
        val hasPassword = credentials.password.value.isNotEmpty()

        _viewState.value = LoginViewState.Active(
            credentials = credentials,
            emailInputErrorMessage = if (hasEmail) {
                null
            } else {
                UIText.ResourceText(R.string.err_empty_email)
            },
            passwordInputErrorMessage = if (hasPassword) {
                null
            } else {
                UIText.ResourceText(R.string.err_empty_password)
            },
        )

        return hasEmail && hasPassword
    }
}

private fun Credentials.withUpdatedEmail(email: String): Credentials {
    return this.copy(email = Email(email))
}

private fun Credentials.withUpdatedPassword(password: String): Credentials {
    return this.copy(password = Password(password))
}
