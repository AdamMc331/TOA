package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeTaskRepository {
    val mock: TaskRepository = mockk()

    fun mockTasksForDateResult(date: LocalDate, response: Result<List<Task>>) {
        coEvery {
            mock.fetchTasksForDate(date, any())
        } returns flowOf(response)
    }

    fun mockUpdateTaskResult(
        task: Task,
        response: Result<Unit>,
    ) {
        coEvery {
            mock.updateTask(task)
        } returns response
    }
}
