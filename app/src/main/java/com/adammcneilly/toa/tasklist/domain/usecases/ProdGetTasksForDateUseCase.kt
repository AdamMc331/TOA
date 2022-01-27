package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import com.adammcneilly.toa.tasklist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ProdGetTasksForDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) : GetTasksForDateUseCase {

    override fun invoke(
        date: LocalDate,
        completed: Boolean,
    ): Flow<TaskListResult> {
        return taskRepository.fetchTasksForDate(date, completed)
    }
}
