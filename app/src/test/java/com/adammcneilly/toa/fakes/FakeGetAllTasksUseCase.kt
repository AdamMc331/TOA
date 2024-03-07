package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.tasklist.domain.usecases.GetAllTasksUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class FakeGetAllTasksUseCase {
    val mock: GetAllTasksUseCase = mockk()

    fun mockResult(
        response: Result<List<Task>>,
    ) {
        coEvery {
            mock.invoke()
        } returns flowOf(response)
    }
}
