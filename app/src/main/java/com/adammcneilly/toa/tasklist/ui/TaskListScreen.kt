package com.adammcneilly.toa.tasklist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.AddTaskDialogDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun TaskListScreen(
    navigator: DestinationsNavigator,
    viewModel: TaskListViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    TaskListContent(
        viewState = viewState.value,
        onRescheduleClicked = {},
        onDoneClicked = viewModel::onDoneButtonClicked,
        onAddButtonClicked = {
            navigator.navigate(AddTaskDialogDestination)
        },
        onPreviousDateButtonClicked = viewModel::onPreviousDateButtonClicked,
        onNextDateButtonClicked = viewModel::onNextDateButtonClicked,
    )
}
