package com.adammcneilly.toa.tasklist.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.adammcneilly.toa.addtask.ui.AddTaskNavArguments
import com.adammcneilly.toa.destinations.AddTaskDialogDestination
import com.adammcneilly.toa.destinations.AddTaskScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TaskListScreen(
    navigator: DestinationsNavigator,
    windowWidthSizeClass: WindowWidthSizeClass,
    viewModel: TaskListViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    TaskListContent(
        viewState = viewState.value,
        onRescheduleClicked = viewModel::onRescheduleButtonClicked,
        onDoneClicked = viewModel::onDoneButtonClicked,
        onAddButtonClicked = {
            val navArgs = AddTaskNavArguments(
                initialDate = viewState.value.selectedDate,
            )

            val destination = if (windowWidthSizeClass != WindowWidthSizeClass.Compact) {
                AddTaskDialogDestination(
                    initialDate = navArgs.initialDate
                )
            } else {
                AddTaskScreenDestination(
                    initialDate = navArgs.initialDate,
                )
            }

            navigator.navigate(destination)
        },
        onDateSelected = viewModel::onDateSelected,
        onTaskRescheduled = viewModel::onTaskRescheduled,
        onReschedulingCompleted = viewModel::onReschedulingCompleted,
        onAlertMessageShown = viewModel::onAlertMessageShown,
        modifier = Modifier
            .testTag(TaskListScreen.TEST_TAG),
    )
}

object TaskListScreen {
    const val TEST_TAG = "TASK_LIST_SCREEN"
}
