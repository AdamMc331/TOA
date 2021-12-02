package com.adammcneilly.toa.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.TaskListScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination(
    start = true,
)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    SideEffect {
        coroutineScope.launch {
            viewModel.loginCompletedChannel.receive()

            navigator.navigate(TaskListScreenDestination)
        }
    }

    LoginContent(
        viewState = viewState.value,
        onEmailChanged = viewModel::emailChanged,
        onPasswordChanged = viewModel::passwordChanged,
        onLoginClicked = viewModel::loginButtonClicked,
        onSignUpClicked = viewModel::signUpButtonClicked,
    )
}
