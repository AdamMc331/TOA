package com.adammcneilly.toa.tasklist.domain.repository

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import kotlinx.coroutines.delay
import javax.inject.Inject

class DemoTaskListRepository @Inject constructor() : TaskListRepository {

    override suspend fun fetchAllTasks(): Result<List<Task>> {
        delay(2_000)

        val tasks = (1..10).map { index ->
            Task(
                description = "Test task: $index",
            )
        }

        return Result.Success(tasks)
    }
}
