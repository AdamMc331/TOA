package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.task.api.TaskListResult
import com.adammcneilly.toa.task.api.test.FakeTaskRepository
import com.adammcneilly.toa.task.api.test.TasksForDateInput
import com.adammcneilly.toa.tasklist.domain.usecases.GetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.RescheduleTaskUseCase
import com.adammcneilly.toa.toEpochMillisUTC
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskListViewModelRobot {
    private val fakeTaskRepository = FakeTaskRepository()
    private val getTasksForDateUseCase = with (fakeTaskRepository) {
        GetTasksForDateUseCase()
    }
    private val rescheduleTaskUseCase = with (fakeTaskRepository) {
        RescheduleTaskUseCase()
    }
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() = apply {
        viewModel = TaskListViewModel(
            getTasksForDateUseCase = getTasksForDateUseCase,
            taskRepository = fakeTaskRepository,
            rescheduleTaskUseCase = rescheduleTaskUseCase,
        )
    }

    fun mockTaskListResultForDate(
        date: LocalDate,
        result: Flow<TaskListResult>,
    ) = apply {
        val input = TasksForDateInput(date.toEpochMillisUTC(), true)
        fakeTaskRepository.tasksForDateResults[input] = result
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
        TODO("Verify that a task was updated to the given date")
//        fakeRescheduleTaskUseCase.assertInvocation(
//            Pair(task, date)
//        )
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
