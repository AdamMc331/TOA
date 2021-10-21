package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task

sealed class TaskListViewState {
    object Loading : TaskListViewState()

    data class Loaded(
        val tasks: List<Task>,
    ) : TaskListViewState()

    data class Error(
        val errorMessage: UIText,
    ) : TaskListViewState()
}
