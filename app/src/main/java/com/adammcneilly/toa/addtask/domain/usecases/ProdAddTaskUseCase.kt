package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.preferences.UserPreferences
import com.adammcneilly.toa.task.api.TaskRepository
import kotlinx.coroutines.flow.first
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class ProdAddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val userPreferences: UserPreferences,
) : AddTaskUseCase {

    @Suppress("ReturnCount")
    override suspend fun invoke(
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
            val preferenceCheckResult = ensureNumTasksWithinPreferences(task, taskRepository, userPreferences)

            if (preferenceCheckResult != null) {
                return preferenceCheckResult
            }
        }

        val result = taskRepository.addTask(sanitizedTask)

        return result.fold(
            onSuccess = {
                AddTaskResult.Success
            },
            onFailure = {
                AddTaskResult.Failure.Unknown
            },
        )
    }

    private suspend fun ensureNumTasksWithinPreferences(
        task: Task,
        taskRepository: TaskRepository,
        userPreferences: UserPreferences,
    ): AddTaskResult.Failure.MaxTasksPerDayExceeded? {
        if (!userPreferences.getPreferredNumTasksPerDayEnabled()) {
            return null
        }

        val preferredNumTasks = userPreferences.getPreferredNumTasksPerDay() ?: return null

        val incompleteTaskList = taskRepository.fetchTasksForDate(
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
}
