package com.adammcneilly.toa.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.Email
import com.adammcneilly.toa.login.domain.model.Password
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Screen
import dagger.hilt.components.SingletonComponent
import kotlinx.parcelize.Parcelize

@Parcelize
object CircuitLoginScreen : Screen {
    data class LoginState(
        val credentials: Credentials,
        val eventSink: (LoginEvent) -> Unit = {},
    ) : CircuitUiState

    sealed interface LoginEvent {
        data class EmailChanged(val email: Email) : LoginEvent

        data class PasswordChanged(val password: Password) : LoginEvent

        object LoginButtonClicked : LoginEvent
    }
}

@CircuitInject(CircuitLoginScreen::class, SingletonComponent::class)
@Composable
fun CircuitLoginPresenter(): CircuitLoginScreen.LoginState {
    var credentials by remember { mutableStateOf(Credentials()) }

    return CircuitLoginScreen.LoginState(credentials) { event ->
        when (event) {
            is CircuitLoginScreen.LoginEvent.EmailChanged -> {
                credentials = credentials.copy(
                    email = event.email,
                )
            }

            CircuitLoginScreen.LoginEvent.LoginButtonClicked -> {
                // Navigate to new screen?
            }

            is CircuitLoginScreen.LoginEvent.PasswordChanged -> {
                credentials = credentials.copy(
                    password = event.password,
                )
            }
        }
    }
}

@CircuitInject(CircuitLoginScreen::class, SingletonComponent::class)
@Composable
fun CircuitLoginContent(
    state: CircuitLoginScreen.LoginState,
    modifier: Modifier = Modifier,
) {
    LoginContent(
        viewState = LoginViewState.Active(
            credentials = state.credentials,
        ),
        onEmailChanged = { email ->
            state.eventSink(CircuitLoginScreen.LoginEvent.EmailChanged(Email(email)))
        },
        onPasswordChanged = { password ->
            state.eventSink(CircuitLoginScreen.LoginEvent.PasswordChanged(Password(password)))
        },
        onLoginClicked = {
            state.eventSink(CircuitLoginScreen.LoginEvent.LoginButtonClicked)
        },
        onSignUpClicked = {
            // TODO: Implement this
        },
        modifier = modifier,
    )
}
