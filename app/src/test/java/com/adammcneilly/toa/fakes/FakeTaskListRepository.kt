package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class FakeTaskListRepository {
    val mock: TaskListRepository = mockk()

    fun mockFetchAllTasksResult(response: Result<List<Task>>) {
        coEvery {
            mock.fetchAllTasks()
        } returns flowOf(response)
    }
}
