package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.fakes.FakeTaskListRepository
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.usecases.ProdGetAllTasksUseCase
import com.google.common.truth.Truth.assertThat

class TaskListViewModelRobot {
    private val fakeTaskListRepository = FakeTaskListRepository()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() = apply {
        viewModel = TaskListViewModel(
            getAllTasksUseCase = ProdGetAllTasksUseCase(
                taskListRepository = fakeTaskListRepository.mock,
            ),
        )
    }

    fun mockAllTasksResult(result: Result<List<Task>>) = apply {
        fakeTaskListRepository.mockFetchAllTasksResult(result)
    }

    fun assertViewState(expectedViewState: TaskListViewState) = apply {
        val actualViewState = viewModel.viewState.value
        assertThat(actualViewState).isEqualTo(expectedViewState)
    }
}
