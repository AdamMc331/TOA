package com.adammcneilly.toa.core.data.local

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import com.adammcneilly.toa.tasklist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
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

    override fun fetchTasksForDate(
        date: LocalDate,
        completed: Boolean,
    ): Flow<TaskListResult> {
        return taskDAO
            .fetchTasksForDate(
                date.toPersistableDateString(),
                completed,
            )
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
        scheduledDateMillis = LocalDate.parse(this.scheduledDate, persistedDateFormatter)
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(),
        completed = this.completed,
    )
}

private fun Task.toPersistableTask(): PersistableTask {
    val scheduledDate = Instant.ofEpochMilli(this.scheduledDateMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return PersistableTask(
        id = this.id,
        description = this.description,
        scheduledDate = scheduledDate.toPersistableDateString(),
        completed = this.completed,
    )
}
