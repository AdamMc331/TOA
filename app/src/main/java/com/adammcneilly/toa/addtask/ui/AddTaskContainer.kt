package com.adammcneilly.toa.addtask.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * A container for the add task screen. Unlike [AddTaskContent], this is a stateful composable
 * that can consume a [viewModel] and [navigator].
 *
 * This is used to share code that appears in two destinations, the [AddTaskScreen] and
 * [AddTaskDialog].
 */
@Composable
fun AddTaskContainer(
    viewModel: AddTaskViewModel,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    val viewState = viewModel.viewState.collectAsState()

    DisposableEffect(viewState.value) {
        if (viewState.value is AddTaskViewState.Completed) {
            navigator.popBackStack()
        }

        onDispose { }
    }

    AddTaskContent(
        viewState = viewState.value,
        onTaskDescriptionChanged = viewModel::onTaskDescriptionChanged,
        onTaskScheduledDateChanged = viewModel::onTaskScheduledDateChanged,
        onSubmitClicked = viewModel::onSubmitButtonClicked,
        modifier = modifier,
    )
}
