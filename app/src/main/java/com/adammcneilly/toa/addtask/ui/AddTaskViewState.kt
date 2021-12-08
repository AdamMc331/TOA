package com.adammcneilly.toa.addtask.ui

import com.adammcneilly.toa.addtask.domain.model.TaskInput
import com.adammcneilly.toa.core.ui.UIText

/**
 * A collection of possible view states for the add task UI.
 */
sealed class AddTaskViewState(
    open val taskInput: TaskInput,
    val inputsEnabled: Boolean = true,
) {

    object Initial : AddTaskViewState(
        taskInput = TaskInput(),
    )

    data class Active(
        override val taskInput: TaskInput,
    ) : AddTaskViewState(
        taskInput = taskInput,
    )

    data class Submitting(
        override val taskInput: TaskInput,
    ) : AddTaskViewState(
        taskInput = taskInput,
        inputsEnabled = false,
    )

    data class SubmissionError(
        override val taskInput: TaskInput,
        val errorMessage: UIText,
    ) : AddTaskViewState(
        taskInput = taskInput,
    )

    object Completed : AddTaskViewState(
        taskInput = TaskInput(),
        inputsEnabled = false,
    )
}
