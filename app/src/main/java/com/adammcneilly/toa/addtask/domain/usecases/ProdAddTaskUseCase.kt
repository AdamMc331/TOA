package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import javax.inject.Inject

class ProdAddTaskUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository,
) : AddTaskUseCase {

    override suspend fun invoke(task: Task): AddTaskResult {
        val result = taskListRepository.addTask(task)

        return when (result) {
            is Result.Success -> AddTaskResult.Success
            is Result.Error -> AddTaskResult.Failure.Unknown
        }
    }
}
