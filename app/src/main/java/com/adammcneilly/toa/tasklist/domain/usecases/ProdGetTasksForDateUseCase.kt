package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import com.adammcneilly.toa.tasklist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import java.time.LocalDate
import javax.inject.Inject

class ProdGetTasksForDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) : GetTasksForDateUseCase {

    override fun invoke(
        date: LocalDate,
        appendCompletedTasks: Boolean,
    ): Flow<TaskListResult> {
        val incompleteTasksFlow = taskRepository.fetchTasksForDate(date, false)
        return if (appendCompletedTasks) getMergedTaskFlow(date, incompleteTasksFlow)
        else incompleteTasksFlow
    }

    private fun getMergedTaskFlow(
        date: LocalDate,
        incompleteTasksFlow: Flow<Result<List<Task>>>
    ): Flow<Result<List<Task>>> {
        val completedTasksFlow = taskRepository.fetchTasksForDate(date, true)
        return incompleteTasksFlow
            .combineTransform(completedTasksFlow) { incomplete, complete ->
                if (incomplete is Result.Success && complete is Result.Success) {
                    val result = Result.Success(incomplete.data + complete.data)
                    emit(result)
                } else {
                    emit(incomplete)
                    emit(complete)
                }
            }
    }
}
