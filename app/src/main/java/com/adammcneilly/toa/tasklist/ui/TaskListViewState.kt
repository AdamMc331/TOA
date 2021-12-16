package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.ui.UIText

sealed class TaskListViewState {
    object Loading : TaskListViewState()

    data class Loaded(
        val tasks: List<TaskDisplayModel>,
    ) : TaskListViewState()

    data class Error(
        val errorMessage: UIText,
    ) : TaskListViewState()
}
