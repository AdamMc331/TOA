package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.preferences.UserPreferences
import com.adammcneilly.toa.task.api.TaskRepository
import kotlinx.coroutines.flow.first
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Function that runs within the context of a [TaskRepository] and [UserPreferences] to insert
 * the supplied [task] into the repo. If [ignoreTaskLimits] is true, we won't respect the limits
 * received from [UserPreferences].
 */
context(TaskRepository, UserPreferences)
suspend fun addTask(
    task: Task,
    ignoreTaskLimits: Boolean,
): AddTaskResult {
    val sanitizedTask = task.copy(
        description = task.description.trim(),
    )

    val validationResult = validateInput(sanitizedTask)

    if (validationResult != null) {
        return validationResult
    }

    if (!ignoreTaskLimits) {
        val preferenceCheckResult = ensureNumTasksWithinPreferences(task)

        if (preferenceCheckResult != null) {
            return preferenceCheckResult
        }
    }

    val result = addTask(sanitizedTask)

    return result.fold(
        onSuccess = {
            AddTaskResult.Success
        },
        onFailure = {
            AddTaskResult.Failure.Unknown
        },
    )
}

context(TaskRepository, UserPreferences)
private suspend fun ensureNumTasksWithinPreferences(
    task: Task,
): AddTaskResult.Failure.MaxTasksPerDayExceeded? {
    if (!getPreferredNumTasksPerDayEnabled()) {
        return null
    }

    val preferredNumTasks = getPreferredNumTasksPerDay() ?: return null

    val incompleteTaskList = fetchTasksForDate(
        dateMillis = task.scheduledDateMillis,
        completed = false,
    ).first().getOrNull()

    val numIncompleteTasks = incompleteTaskList?.size ?: 0

    return if (numIncompleteTasks >= preferredNumTasks) {
        AddTaskResult.Failure.MaxTasksPerDayExceeded
    } else {
        null
    }
}

/**
 * Since it's no longer possible to select a date in the past (our date picker validates this),
 * we can simplify this to only validate that the description is not empty.
 */
private fun validateInput(task: Task): AddTaskResult.Failure.InvalidInput? {
    val emptyDescription = task.description.isBlank()

    val scheduledDate = Instant
        .ofEpochMilli(task.scheduledDateMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val scheduledDateInPast = scheduledDate.isBefore(LocalDate.now())

    return if (emptyDescription || scheduledDateInPast) {
        AddTaskResult.Failure.InvalidInput(
            emptyDescription = emptyDescription,
            scheduledDateInPast = scheduledDateInPast,
        )
    } else {
        null
    }
}
