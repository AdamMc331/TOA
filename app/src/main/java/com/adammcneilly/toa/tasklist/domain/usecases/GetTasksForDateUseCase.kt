package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Fetches all tasks for a given date.
 */
interface GetTasksForDateUseCase {
    operator fun invoke(date: LocalDate): Flow<TaskListResult>
}
