package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Fetches all tasks for a specific date and completed status.
 */
interface GetTasksForDateUseCase {
    /**
     * Fetch tasks for the given [date], where the completed property of the task matches the supplied
     * [appendCompletedTasks] argument.
     */
    operator fun invoke(
        date: LocalDate,
        appendCompletedTasks: Boolean,
    ): Flow<TaskListResult>
}
