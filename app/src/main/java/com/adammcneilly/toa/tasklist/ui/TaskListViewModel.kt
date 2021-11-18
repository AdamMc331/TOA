package com.adammcneilly.toa.tasklist.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
class TaskListViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(TaskListViewState.Loading)
    val viewState: StateFlow<TaskListViewState> = _viewState
}
