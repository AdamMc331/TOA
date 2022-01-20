package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import java.time.LocalDate

/**
 * All of the necessary configurations for the task list screen UI.
 */
data class TaskListViewState(
    val showLoading: Boolean = true,
    val tasks: List<Task>? = null,
    val errorMessage: UIText? = null,
    val selectedDate: LocalDate = LocalDate.now(),
)
