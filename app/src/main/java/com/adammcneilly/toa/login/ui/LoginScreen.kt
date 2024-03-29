package com.adammcneilly.toa.login.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.adammcneilly.toa.destinations.LoginScreenDestination
import com.adammcneilly.toa.destinations.TaskListScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    start = true,
)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    DisposableEffect(viewState.value) {
        if (viewState.value is LoginViewState.Completed) {
            navigator.navigate(TaskListScreenDestination()) {
                this.popUpTo(LoginScreenDestination.route) {
                    this.inclusive = true
                }
            }
        }

        onDispose { }
    }

    val context = LocalContext.current

    LoginContent(
        viewState = viewState.value,
        onEmailChanged = viewModel::emailChanged,
        onPasswordChanged = viewModel::passwordChanged,
        onLoginClicked = viewModel::loginButtonClicked,
        onSignUpClicked = {
            Toast.makeText(
                context,
                "Not supported.",
                Toast.LENGTH_SHORT,
            ).show()
        },
        modifier = Modifier
            .testTag(LoginScreen.TEST_TAG),
    )
}

object LoginScreen {
    const val TEST_TAG = "LOGIN_SCREEN"
}
