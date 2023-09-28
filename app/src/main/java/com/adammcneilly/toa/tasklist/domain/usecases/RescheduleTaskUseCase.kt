package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.TaskRepository
import com.adammcneilly.toa.toEpochMillis
import java.time.LocalDate
import javax.inject.Inject

/**
 * Concrete implementation of a use case to reschedule a task that will save the task
 * change inside of the given [TaskRepository].
 */
context(TaskRepository)
class RescheduleTaskUseCase @Inject constructor() {

    suspend fun invoke(task: Task, newDate: LocalDate) {
        val updatedTask = task.copy(
            scheduledDateMillis = newDate.toEpochMillis(),
        )

        updateTask(updatedTask)
    }
}
