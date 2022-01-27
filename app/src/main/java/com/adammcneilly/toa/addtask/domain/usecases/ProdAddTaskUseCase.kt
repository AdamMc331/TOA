package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskRepository
import java.time.LocalDate
import javax.inject.Inject

class ProdAddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) : AddTaskUseCase {

    override suspend fun invoke(task: Task): AddTaskResult {
        val validationResult = validateInput(task)

        if (validationResult != null) {
            return validationResult
        }

        val result = taskRepository.addTask(task)

        return when (result) {
            is Result.Success -> AddTaskResult.Success
            is Result.Error -> AddTaskResult.Failure.Unknown
        }
    }

    private fun validateInput(task: Task): AddTaskResult.Failure.InvalidInput? {
        val emptyDescription = task.description.isEmpty()
        val scheduledDateInPast = task.scheduledDate.isBefore(LocalDate.now())

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
