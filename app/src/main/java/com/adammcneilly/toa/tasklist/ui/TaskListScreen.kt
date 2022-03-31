package com.adammcneilly.toa.tasklist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.adammcneilly.toa.core.ui.WindowSize
import com.adammcneilly.toa.destinations.AddTaskDialogDestination
import com.adammcneilly.toa.destinations.AddTaskScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun TaskListScreen(
    navigator: DestinationsNavigator,
    windowSize: WindowSize = WindowSize.Compact,
    viewModel: TaskListViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    TaskListContent(
        viewState = viewState.value,
        onRescheduleClicked = {},
        onDoneClicked = viewModel::onDoneButtonClicked,
        onAddButtonClicked = {
            // Update this to pass the selected as an argument to each destination
            val destination = if (windowSize != WindowSize.Compact) {
                AddTaskDialogDestination(
                    initialDate = viewState.value.selectedDate,
                )
            } else {
                AddTaskScreenDestination(
                    initialDate = viewState.value.selectedDate,
                )
            }

            navigator.navigate(destination)
        },
        onPreviousDateButtonClicked = viewModel::onPreviousDateButtonClicked,
        onNextDateButtonClicked = viewModel::onNextDateButtonClicked,
        onDateSelected = viewModel::onDateSelected,
    )
}
