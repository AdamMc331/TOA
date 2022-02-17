package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.ZoneId

class FakeTaskRepository {
    val mock: TaskRepository = mockk()

    fun mockTasksForDateResult(date: LocalDate, response: Result<List<Task>>) {
        val dateMillis = date.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        coEvery {
            mock.fetchTasksForDate(dateMillis, any())
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
