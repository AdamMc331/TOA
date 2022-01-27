package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import javax.inject.Inject

/**
 * A concrete implementation of [MarkTaskAsCompleteUseCase] which will modify the task
 * and update it inside our [taskListRepository].
 */
class ProdMarkTaskAsCompleteUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository,
) : MarkTaskAsCompleteUseCase {

    override suspend fun invoke(task: Task): Result<Unit> {
        val completedTask = task.copy(completed = true)

        return taskListRepository.updateTask(completedTask)
    }
}
