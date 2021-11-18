package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import javax.inject.Inject

class ProdGetAllTasksUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository,
) : GetAllTasksUseCase {

    override suspend fun invoke(): Result<List<Task>> {
        return taskListRepository.fetchAllTasks()
    }
}
