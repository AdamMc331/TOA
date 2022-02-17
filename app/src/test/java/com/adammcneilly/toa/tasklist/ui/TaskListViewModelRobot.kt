package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.test.FakeTaskRepository
import com.adammcneilly.toa.tasklist.domain.usecases.ProdGetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.ProdMarkTaskAsCompleteUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.ZoneId

class TaskListViewModelRobot {
    private val fakeTaskRepository = FakeTaskRepository()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() = apply {
        viewModel = TaskListViewModel(
            getTasksForDateUseCase = ProdGetTasksForDateUseCase(
                taskRepository = fakeTaskRepository,
            ),
            markTaskAsCompleteUseCase = ProdMarkTaskAsCompleteUseCase(
                taskRepository = fakeTaskRepository,
            )
        )
    }

    fun mockTasksForDateResult(
        date: LocalDate,
        result: Result<List<Task>>,
    ) = apply {
        val dateMillis = date.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val completedInput = Pair(dateMillis, true)
        fakeTaskRepository.tasksForDateResults[completedInput] = flowOf(result)

        val incompleteInput = Pair(dateMillis, false)
        fakeTaskRepository.tasksForDateResults[incompleteInput] = flowOf(result)
    }

    fun assertViewState(expectedViewState: TaskListViewState) = apply {
        val actualViewState = viewModel.viewState.value
        assertThat(actualViewState).isEqualTo(expectedViewState)
    }

    fun clickPreviousDateButton() = apply {
        viewModel.onPreviousDateButtonClicked()
    }

    fun clickNextDateButton() = apply {
        viewModel.onNextDateButtonClicked()
    }
}
