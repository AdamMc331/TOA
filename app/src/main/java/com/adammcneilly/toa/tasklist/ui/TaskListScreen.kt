package com.adammcneilly.toa.tasklist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    TaskListContent(
        viewState = viewState.value,
        onRescheduleClicked = {},
        onDoneClicked = {},
        onAddButtonClicked = {},
    )
}
