package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeTaskListRepository {
    val mock: TaskRepository = mockk()

    fun mockTasksForDateResult(date: LocalDate, response: Result<List<Task>>) {
        coEvery {
            mock.fetchTasksForDate(date)
        } returns flowOf(response)
    }
}
