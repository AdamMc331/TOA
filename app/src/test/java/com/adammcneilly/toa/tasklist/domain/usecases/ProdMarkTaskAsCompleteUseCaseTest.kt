package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.fakes.FakeTaskRepository
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate

class ProdMarkTaskAsCompleteUseCaseTest {
    private val fakeTaskRepository = FakeTaskRepository()

    @Test
    fun invoke() = runTest {
        val initialTask = Task(
            id = "",
            description = "",
            scheduledDate = LocalDate.now(),
            completed = false,
        )

        val completedTask = initialTask.copy(
            completed = true,
        )

        val useCase = ProdMarkTaskAsCompleteUseCase(
            taskRepository = fakeTaskRepository.mock,
        )

        val mockResult = Result.Success(Unit)

        fakeTaskRepository.mockUpdateTaskResult(
            task = completedTask,
            response = mockResult,
        )
        val actualResult = useCase.invoke(initialTask)
        assertThat(actualResult).isEqualTo(mockResult)
    }
}
