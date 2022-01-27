package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.fakes.FakeTaskRepository
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.usecases.ProdGetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.ProdMarkTaskAsCompleteUseCase
import com.google.common.truth.Truth.assertThat
import java.time.LocalDate

class TaskListViewModelRobot {
    private val fakeTaskRepository = FakeTaskRepository()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() = apply {
        viewModel = TaskListViewModel(
            getTasksForDateUseCase = ProdGetTasksForDateUseCase(
                taskRepository = fakeTaskRepository.mock,
            ),
            markTaskAsCompleteUseCase = ProdMarkTaskAsCompleteUseCase(
                taskRepository = fakeTaskRepository.mock,
            )
        )
    }

    fun mockTasksForDateResult(
        date: LocalDate,
        result: Result<List<Task>>,
    ) = apply {
        fakeTaskRepository.mockTasksForDateResult(date, result)
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
