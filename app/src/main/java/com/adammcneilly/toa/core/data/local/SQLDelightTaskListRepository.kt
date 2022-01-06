package com.adammcneilly.toa.core.data.local

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.sqldelight.Sqldelight_task
import com.adammcneilly.toa.sqldelight.TaskQueries
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SQLDelightTaskListRepository @Inject constructor(
    private val taskQueries: TaskQueries,
) : TaskListRepository {

    override fun fetchAllTasks(): Flow<Result<List<Task>>> {
        return taskQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .map { taskList ->
                val domainTasks = taskList.map(Sqldelight_task::toTask)

                Result.Success(domainTasks)
            }
    }

    override suspend fun addTask(task: Task): Result<Unit> {
        taskQueries.insertTask(task.toPersistableTask())

        return Result.Success(Unit)
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun markAsComplete(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }
}

private const val PERSISTED_DATE_FORMAT = "yyyy-MM-dd"
private val persistedDateFormatter = DateTimeFormatter.ofPattern(PERSISTED_DATE_FORMAT)

private fun Sqldelight_task.toTask(): Task {
    return Task(
        id = this.id,
        description = this.description,
        scheduledDate = LocalDate.parse(this.scheduledDate, persistedDateFormatter),
    )
}

private fun Task.toPersistableTask(): Sqldelight_task {
    return Sqldelight_task(
        id = this.id,
        description = this.description,
        scheduledDate = persistedDateFormatter.format(this.scheduledDate),
    )
}
