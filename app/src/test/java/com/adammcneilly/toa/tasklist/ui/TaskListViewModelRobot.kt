package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.fakes.FakeGetTasksForDateUseCase
import com.adammcneilly.toa.fakes.FakeRescheduleTaskUseCase
import com.adammcneilly.toa.task.api.TaskListResult
import com.adammcneilly.toa.task.api.test.FakeTaskRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskListViewModelRobot {
    private val fakeTaskRepository = FakeTaskRepository()
    private val fakeGetTasksForDateUseCase = FakeGetTasksForDateUseCase()
    private val fakeRescheduleTaskUseCase = FakeRescheduleTaskUseCase()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() = apply {
        viewModel = TaskListViewModel(
            getTasksForDateUseCase = fakeGetTasksForDateUseCase,
            taskRepository = fakeTaskRepository,
            rescheduleTaskUseCase = fakeRescheduleTaskUseCase,
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

    fun clickRescheduleButton(task: Task) = apply {
        viewModel.onRescheduleButtonClicked(task)
    }

    fun rescheduleTaskForDate(
        task: Task,
        date: LocalDate,
    ) = apply {
        viewModel.onTaskRescheduled(task, date)
    }

    fun assertTaskRescheduledForDate(
        task: Task,
        date: LocalDate,
    ) = apply {
        fakeRescheduleTaskUseCase.assertInvocation(
            Pair(task, date),
        )
    }

    /**
     * Look up and dismiss first alert message ID
     */
    fun showAlertMessage() = apply {
        viewModel.viewState.value.alertMessages.firstOrNull()?.id?.let { id ->
            viewModel.onAlertMessageShown(id)
        }
    }

    fun dismissAlertMessage() = apply {
        viewModel.viewState.value.alertMessages?.firstOrNull()?.onDismissed?.invoke()
    }
}
