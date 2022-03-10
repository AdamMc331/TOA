package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.task.api.TaskListResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Fetches all tasks for a specific date.
 */
interface GetTasksForDateUseCase {
    /**
     * Fetch tasks for the given [date].
     */
    operator fun invoke(
        date: LocalDate,
    ): Flow<TaskListResult>
}
