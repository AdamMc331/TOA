package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.task.api.TaskListResult
import com.adammcneilly.toa.task.api.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class ProdGetTasksForDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) : GetTasksForDateUseCase {
    override fun invoke(
        date: LocalDate,
    ): Flow<TaskListResult> {
        val dateMillis = date.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val incompleteTaskFlow = taskRepository.fetchTasksForDate(dateMillis, completed = false)
        val completedTaskFlow = taskRepository.fetchTasksForDate(dateMillis, completed = true)

        return incompleteTaskFlow.combineTransform(completedTaskFlow) { incomplete, complete ->
            val incompleteTasks = incomplete.getOrNull()
            val completeTasks = complete.getOrNull()
            if (incompleteTasks != null && completeTasks != null) {
                val result = Result.success(incompleteTasks + completeTasks)
                emit(result)
            } else {
                // Ideally we emit one of the actual errors that occurred, but for now we can just
                // say something went wrong.
                emit(Result.failure(Throwable("Error Requesting Tasks For Date: $date")))
            }
        }
    }
}
