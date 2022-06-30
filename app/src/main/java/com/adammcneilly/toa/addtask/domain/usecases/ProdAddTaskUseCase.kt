package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.data.Result
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

    override suspend fun invoke(
        task: Task,
        ignoreTaskLimits: Boolean,
    ): AddTaskResult {
        // Just for testing
        userPreferences.setPreferredNumTasksPerDay(3)

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

        return when (result) {
            is Result.Success -> AddTaskResult.Success
            is Result.Error -> AddTaskResult.Failure.Unknown
        }
    }

    private suspend fun ensureNumTasksWithinPreferences(
        task: Task,
        taskRepository: TaskRepository,
        userPreferences: UserPreferences,
    ): AddTaskResult.Failure.MaxTasksPerDayExceeded? {
        val preferredNumTasks = userPreferences.getPreferredNumTasksPerDay() ?: return null

        val numIncompleteTasksResult = taskRepository.fetchTasksForDate(
            dateMillis = task.scheduledDateMillis,
            completed = false,
        ).first() as? Result.Success

        val numIncompleteTasks = numIncompleteTasksResult?.data?.size ?: 0

        return if (numIncompleteTasks >= preferredNumTasks) {
            AddTaskResult.Failure.MaxTasksPerDayExceeded
        } else {
            null
        }
    }

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
