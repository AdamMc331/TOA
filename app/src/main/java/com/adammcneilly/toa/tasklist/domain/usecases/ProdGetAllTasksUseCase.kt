package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProdGetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) : GetAllTasksUseCase {

    override fun invoke(): Flow<Result<List<Task>>> {
        return taskRepository.fetchAllTasks()
    }
}
