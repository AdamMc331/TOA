package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.fakes.FakeGetTasksForDateUseCase
import com.adammcneilly.toa.fakes.FakeTaskRepository
import com.adammcneilly.toa.tasklist.domain.repository.TaskListResult
import com.adammcneilly.toa.tasklist.domain.usecases.ProdMarkTaskAsCompleteUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskListViewModelRobot {
    private val fakeTaskRepository = FakeTaskRepository()
    private val fakeGetTasksForDateUseCase = FakeGetTasksForDateUseCase()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() = apply {
        viewModel = TaskListViewModel(
            getTasksForDateUseCase = fakeGetTasksForDateUseCase,
            markTaskAsCompleteUseCase = ProdMarkTaskAsCompleteUseCase(
                taskRepository = fakeTaskRepository.mock,
            )
        )
    }

    fun mockTaskListResultForDate(
        date: LocalDate,
        result: Flow<TaskListResult>,
    ) = apply {
        fakeGetTasksForDateUseCase.mockResultForDate(date, result)
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
