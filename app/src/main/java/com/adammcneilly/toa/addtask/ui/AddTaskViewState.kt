package com.adammcneilly.toa.addtask.ui

import com.adammcneilly.toa.R
import com.adammcneilly.toa.addtask.domain.model.TaskInput
import com.adammcneilly.toa.core.ui.UIText
import java.time.LocalDate

/**
 * A collection of possible view states for the add task UI.
 */
sealed class AddTaskViewState(
    open val taskInput: TaskInput,
    val inputsEnabled: Boolean = true,
) {

    data class Initial(
        val initialDate: LocalDate = LocalDate.now(),
    ) : AddTaskViewState(
        taskInput = TaskInput(
            scheduledDate = initialDate,
        ),
    )

    data class Active(
        override val taskInput: TaskInput,
        val descriptionInputErrorMessage: UIText? = null,
        val scheduledDateInputErrorMessage: UIText? = null,
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
        val overrideButtonText: UIText? = null,
    ) : AddTaskViewState(
        taskInput = taskInput,
    )

    object Completed : AddTaskViewState(
        taskInput = TaskInput(),
        inputsEnabled = false,
    )
}

/**
 * This is a helper extension property that will return the submit button text, in all
 * cases except where we are in a [AddTaskViewState.SubmissionError] and allowing the user
 * to try again.
 */
val AddTaskViewState.submitButtonText: UIText
    get() = if (this is AddTaskViewState.SubmissionError && this.overrideButtonText != null) {
        this.overrideButtonText
    } else {
        UIText.ResourceText(R.string.submit)
    }
