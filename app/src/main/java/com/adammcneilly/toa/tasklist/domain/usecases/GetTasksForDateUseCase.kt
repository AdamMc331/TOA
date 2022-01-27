package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Fetches all tasks for a given date.
 */
interface GetTasksForDateUseCase {
    /**
     * Fetch tasks for the given [date] that are [completed] or not.
     */
    operator fun invoke(
        date: LocalDate,
        completed: Boolean,
    ): Flow<TaskListResult>
}
