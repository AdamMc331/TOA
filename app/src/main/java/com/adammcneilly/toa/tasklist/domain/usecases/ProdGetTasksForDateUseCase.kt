package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

class ProdGetTasksForDateUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository,
) : GetTasksForDateUseCase {

    override fun invoke(date: LocalDate): Flow<TaskListResult> {
        return taskListRepository.fetchTasksForDate(date).onEach {
            delay(1000)
        }
    }
}
