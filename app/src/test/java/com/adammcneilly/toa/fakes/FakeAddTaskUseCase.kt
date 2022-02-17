package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.addtask.domain.usecases.AddTaskUseCase
import com.adammcneilly.toa.core.models.Task
import io.mockk.coEvery
import io.mockk.mockk

class FakeAddTaskUseCase {
    val mock: AddTaskUseCase = mockk()

    fun mockResultForTask(
        task: com.adammcneilly.toa.core.models.Task,
        result: AddTaskResult
    ) {
        coEvery {
            mock.invoke(any())
        } returns result
    }
}
