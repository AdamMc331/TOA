package com.adammcneilly.toa.core.data.local

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import com.adammcneilly.toa.tasklist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RoomTaskRepository @Inject constructor(
    private val taskDAO: TaskDAO,
) : TaskRepository {

    override fun fetchAllTasks(): Flow<Result<List<Task>>> {
        return taskDAO
            .fetchAllTasks()
            .map { taskList ->
                Result.Success(taskList.toDomainTaskList())
            }
    }

    override fun fetchTasksForDate(date: LocalDate): Flow<TaskListResult> {
        return taskDAO
            .fetchTasksForDate(date.toPersistableDateString())
            .map { taskList ->
                Result.Success(taskList.toDomainTaskList())
            }
    }

    override suspend fun addTask(task: Task): Result<Unit> {
        taskDAO.insertTask(task.toPersistableTask())

        return Result.Success(Unit)
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        taskDAO.updateTask(task.toPersistableTask())

        return Result.Success(Unit)
    }
}

private fun List<PersistableTask>.toDomainTaskList(): List<Task> {
    return this.map(PersistableTask::toTask)
}

private const val PERSISTED_DATE_FORMAT = "yyyy-MM-dd"
private val persistedDateFormatter = DateTimeFormatter.ofPattern(PERSISTED_DATE_FORMAT)

private fun LocalDate.toPersistableDateString(): String {
    return persistedDateFormatter.format(this)
}

private fun PersistableTask.toTask(): Task {
    return Task(
        id = this.id,
        description = this.description,
        scheduledDate = LocalDate.parse(this.scheduledDate, persistedDateFormatter),
        completed = this.completed,
    )
}

private fun Task.toPersistableTask(): PersistableTask {
    return PersistableTask(
        id = this.id,
        description = this.description,
        scheduledDate = this.scheduledDate.toPersistableDateString(),
        completed = this.completed,
    )
}
