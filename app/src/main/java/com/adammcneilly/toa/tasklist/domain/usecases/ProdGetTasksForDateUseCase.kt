package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.task.api.TaskListResult
import com.adammcneilly.toa.task.api.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class ProdGetTasksForDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) : GetTasksForDateUseCase {

    override fun invoke(
        date: LocalDate,
        completed: Boolean,
    ): Flow<TaskListResult> {
        val dateMillis = date.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        return taskRepository.fetchTasksForDate(dateMillis, completed)
    }
}
