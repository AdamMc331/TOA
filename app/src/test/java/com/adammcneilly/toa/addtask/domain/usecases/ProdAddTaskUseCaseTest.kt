package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.fakes.FakeTaskRepository
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.LocalDate

class ProdAddTaskUseCaseTest {
    private val fakeTaskRepository = FakeTaskRepository()

    private val useCase = ProdAddTaskUseCase(
        taskRepository = fakeTaskRepository.mock,
    )

    @Test
    fun submitWithEmptyDescription() = runBlockingTest {
        val taskToSubmit = Task(
            id = "Testing",
            description = "",
            scheduledDate = LocalDate.now(),
            completed = false,
        )

        val expectedResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = true,
            scheduledDateInPast = false,
        )

        val actualResult = useCase.invoke(taskToSubmit)
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun submitWithScheduledDateInPast() = runBlockingTest {
        val taskToSubmit = Task(
            id = "Testing",
            description = "Some description",
            scheduledDate = LocalDate.now().minusDays(1),
            completed = false,
        )

        val expectedResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = false,
            scheduledDateInPast = true,
        )

        val actualResult = useCase.invoke(taskToSubmit)
        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
