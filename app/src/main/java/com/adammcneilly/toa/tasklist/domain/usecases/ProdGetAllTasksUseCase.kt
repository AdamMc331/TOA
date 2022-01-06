package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProdGetAllTasksUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository,
) : GetAllTasksUseCase {

    override fun invoke(): Flow<Result<List<Task>>> {
        return taskListRepository.fetchAllTasks()
    }
}
