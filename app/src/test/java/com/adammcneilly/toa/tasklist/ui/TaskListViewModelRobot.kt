package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.fakes.FakeGetAllTasksUseCase
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.google.common.truth.Truth.assertThat

class TaskListViewModelRobot {
    private val getAllTasksUseCase = FakeGetAllTasksUseCase()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() = apply {
        viewModel = TaskListViewModel(
            getAllTasksUseCase = getAllTasksUseCase.mock,
        )
    }

    fun mockAllTasksResult(result: Result<List<Task>>) = apply {
        getAllTasksUseCase.mockResult(result)
    }

    fun assertViewState(expectedViewState: TaskListViewState) = apply {
        val actualViewState = viewModel.viewState.value
        assertThat(actualViewState).isEqualTo(expectedViewState)
    }
}
