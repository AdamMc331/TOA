package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.fakes.FakeTaskListRepository
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.usecases.ProdGetTasksForDateUseCase
import com.google.common.truth.Truth.assertThat
import java.time.LocalDate

class TaskListViewModelRobot {
    private val fakeTaskListRepository = FakeTaskListRepository()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() = apply {
        viewModel = TaskListViewModel(
            getTasksForDateUseCase = ProdGetTasksForDateUseCase(
                taskListRepository = fakeTaskListRepository.mock,
            ),
        )
    }

    fun mockTasksForDateResult(
        date: LocalDate,
        result: Result<List<Task>>,
    ) = apply {
        fakeTaskListRepository.mockTasksForDateResult(date, result)
    }

    fun assertViewState(expectedViewState: TaskListViewState) = apply {
        val actualViewState = viewModel.viewState.value
        assertThat(actualViewState).isEqualTo(expectedViewState)
    }
}
