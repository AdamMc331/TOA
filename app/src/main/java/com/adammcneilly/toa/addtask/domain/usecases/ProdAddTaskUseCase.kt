package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.TaskRepository
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class ProdAddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) : AddTaskUseCase {

    override suspend fun invoke(task: Task): AddTaskResult {
        val sanitizedTask = task.copy(
            description = task.description.trim(),
        )

        val validationResult = validateInput(sanitizedTask)

        if (validationResult != null) {
            return validationResult
        }

        val result = taskRepository.addTask(sanitizedTask)

        return when (result) {
            is Result.Success -> AddTaskResult.Success
            is Result.Error -> AddTaskResult.Failure.Unknown
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
