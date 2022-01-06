package com.adammcneilly.toa.tasklist.domain.repository

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DemoTaskListRepository @Inject constructor() : TaskListRepository {

    private val tasks = (1..10).map { index ->
        Task(
            description = "Test task: $index",
        )
    }.toMutableList()

    private val tasksFlow = MutableStateFlow(tasks)

    override fun fetchAllTasks(): Flow<Result<List<Task>>> {
        return tasksFlow.map { tasks ->
            Result.Success(tasks)
        }
    }

    override suspend fun addTask(task: Task): Result<Unit> {
        tasks.add(0, task)

        tasksFlow.value = tasks

        return Result.Success(Unit)
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun markAsComplete(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }
}
