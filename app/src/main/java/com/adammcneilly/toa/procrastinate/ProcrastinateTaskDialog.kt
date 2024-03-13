package com.adammcneilly.toa.procrastinate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Destination(
    style = DestinationStyle.Dialog::class,
)
@Composable
fun ProcrastinateTaskDialog(
    navigator: DestinationsNavigator,
    viewModel: ProcrastinateTaskViewModel = hiltViewModel(),
) {
    val viewState = viewModel.state.collectAsState()

    DisposableEffect(viewState.value) {
        if (viewState.value.isComplete) {
            navigator.popBackStack()
        }

        onDispose { }
    }

    ProcrastinateTaskContent(
        state = viewState.value,
    )
}
