package com.adammcneilly.toa.procrastinate

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
        onSelectionChanged = viewModel::optionSelected,
        onProcrastinateClicked = {
            // TODO: Get our task and pass it into this callback.
        },
        onFutureDateChanged = viewModel::futureDateChanged,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background),
    )
}
