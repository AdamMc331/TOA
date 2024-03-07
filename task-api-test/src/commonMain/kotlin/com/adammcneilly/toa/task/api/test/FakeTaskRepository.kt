package com.adammcneilly.toa.task.api.test

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.TaskListResult
import com.adammcneilly.toa.task.api.TaskRepository
import kotlinx.coroutines.flow.Flow

typealias TasksForDateInput = Pair<Long, Boolean>

/**
 * A concrete implementation of [TaskRepository] that allows to caller to mock and verify calls to this repo.
 */
class FakeTaskRepository : TaskRepository {
    lateinit var allTasksResult: Flow<TaskListResult>

    val tasksForDateResults: MutableMap<TasksForDateInput, Flow<TaskListResult>> = mutableMapOf()

    val addTaskResults: MutableMap<Task, Result<Unit>> = mutableMapOf()

    val updateTaskResults: MutableMap<Task, Result<Unit>> = mutableMapOf()

    override fun fetchAllTasks(): Flow<TaskListResult> {
        return allTasksResult
    }

    override fun fetchTasksForDate(
        dateMillis: Long,
        completed: Boolean,
    ): Flow<TaskListResult> {
        val inputPair = Pair(dateMillis, completed)

        return tasksForDateResults[inputPair]!!
    }

    override suspend fun addTask(
        task: Task,
    ): Result<Unit> {
        return addTaskResults[task]!!
    }

    override suspend fun deleteTask(
        task: Task,
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(
        task: Task,
    ): Result<Unit> {
        return updateTaskResults[task]!!
    }
}
