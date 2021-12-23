package com.adammcneilly.toa.tasklist.domain.repository

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import kotlinx.coroutines.delay
import javax.inject.Inject

class DemoTaskListRepository @Inject constructor() : TaskListRepository {

    private val tasks = (1..10).map { index ->
        Task(
            description = "Test task: $index",
        )
    }.toMutableList()

    override suspend fun fetchAllTasks(): Result<List<Task>> {
        @Suppress("MagicNumber")
        delay(2_000)

        return Result.Success(tasks)
    }

    override suspend fun addTask(task: Task): Result<Unit> {
        tasks.add(0, task)

        return Result.Success(Unit)
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun markAsComplete(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }
}
