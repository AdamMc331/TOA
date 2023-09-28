package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.test.FakeTaskRepository
import com.adammcneilly.toa.toEpochMillis
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate

class RescheduleTaskUseCaseTest {
    private val fakeRepository = FakeTaskRepository()
    private val useCase = with(fakeRepository) {
        RescheduleTaskUseCase()
    }

    @Test
    fun rescheduleTask() = runTest {
        val initialTask = Task(
            id = "TestID",
            description = "Test Task",
            scheduledDateMillis = 0L,
            completed = false,
        )

        val newDate = LocalDate.now().plusDays(1)

        val expectedNewTask = initialTask.copy(
            scheduledDateMillis = newDate.toEpochMillis(),
        )

        fakeRepository.updateTaskResults[expectedNewTask] = Result.success(Unit)

        useCase.invoke(initialTask, newDate)
    }
}
