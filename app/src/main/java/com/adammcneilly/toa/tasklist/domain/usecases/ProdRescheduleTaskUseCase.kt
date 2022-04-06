package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.TaskRepository
import com.adammcneilly.toa.toEpochMillis
import java.time.LocalDate
import javax.inject.Inject

/**
 * Concrete implementation of a [RescheduleTaskUseCase] that will save the task
 * change inside of the given [repository].
 */
class ProdRescheduleTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
) : RescheduleTaskUseCase {

    override suspend fun invoke(task: Task, newDate: LocalDate) {
        val updatedTask = task.copy(
            scheduledDateMillis = newDate.toEpochMillis(),
        )
    }
}
