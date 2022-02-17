package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.test.FakeTaskRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class ProdAddTaskUseCaseTest {
    private val fakeTaskRepository = FakeTaskRepository()

    private val useCase = ProdAddTaskUseCase(
        taskRepository = fakeTaskRepository,
    )

    @Test
    fun submitWithEmptyDescription() = runBlockingTest {
        val taskToSubmit = Task(
            id = "Testing",
            description = "",
            scheduledDateMillis = ZonedDateTime.now()
                .toInstant()
                .toEpochMilli(),
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
            scheduledDateMillis = LocalDate.now().minusDays(1)
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
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
