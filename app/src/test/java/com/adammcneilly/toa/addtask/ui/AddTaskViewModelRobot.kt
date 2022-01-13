package com.adammcneilly.toa.addtask.ui

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.fakes.FakeAddTaskUseCase
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.google.common.truth.Truth.assertThat
import java.time.LocalDate

class AddTaskViewModelRobot {
    private val fakeAddTaskUseCase = FakeAddTaskUseCase()
    private lateinit var viewModel: AddTaskViewModel

    fun buildViewModel() = apply {
        viewModel = AddTaskViewModel(
            addTaskUseCase = fakeAddTaskUseCase.mock,
        )
    }

    fun mockResultForTask(
        task: Task,
        result: AddTaskResult
    ) = apply {
        fakeAddTaskUseCase.mockResultForTask(task, result)
    }

    fun enterDescription(
        newDescription: String,
    ) = apply {
        viewModel.onTaskDescriptionChanged(newDescription)
    }

    fun selectDate(
        newScheduledDate: LocalDate,
    ) = apply {
        viewModel.onTaskScheduledDateChanged(newScheduledDate)
    }

    fun clickSubmit() = apply {
        viewModel.onSubmitButtonClicked()
    }

    fun assertViewState(
        expectedViewState: AddTaskViewState,
    ) = apply {
        val actualViewState = viewModel.viewState.value
        assertThat(actualViewState).isEqualTo(expectedViewState)
    }
}
