package com.adammcneilly.toa.core.data.local

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class RoomTaskListRepository @Inject constructor(
    private val taskDAO: TaskDAO,
) : TaskListRepository {

    override fun fetchAllTasks(): Flow<Result<List<Task>>> {
        return taskDAO
            .fetchAllTasks()
            .map { taskList ->
                val domainTasks = taskList.map(PersistableTask::toTask)

                Result.Success(domainTasks)
            }
    }

    override suspend fun addTask(task: Task): Result<Unit> {
        taskDAO.insertTask(task.toPersistableTask())

        return Result.Success(Unit)
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun markAsComplete(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }
}

private fun PersistableTask.toTask(): Task {
    return Task(
        description = this.description,
    )
}

private fun Task.toPersistableTask(): PersistableTask {
    return PersistableTask(
        id = UUID.randomUUID().toString(),
        description = this.description,
    )
}
