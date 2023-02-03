package com.adammcneilly.toa.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.core.ui.AlertMessage
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.task.api.TaskRepository
import com.adammcneilly.toa.tasklist.domain.usecases.GetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.RescheduleTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasksForDateUseCase: GetTasksForDateUseCase,
    private val rescheduleTaskUseCase: RescheduleTaskUseCase,
    private val taskRepository: TaskRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow(TaskListViewState())
    val viewState = _viewState.asStateFlow()

    init {
        _viewState
            .map { viewState ->
                viewState.selectedDate
            }
            .distinctUntilChanged()
            .flatMapLatest { selectedDate ->
                _viewState.update {
                    it.copy(
                        showLoading = true,
                        incompleteTasks = null,
                        completedTasks = null,
                    )
                }

                getTasksForDateUseCase.invoke(
                    date = selectedDate,
                )
            }
            .onEach { result ->
                _viewState.update {
                    getViewStateForTaskListResult(result)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getViewStateForTaskListResult(result: Result<List<Task>>): TaskListViewState {
        return result.fold(
            onSuccess = { taskList ->
                val (complete, incomplete) = taskList.partition { task ->
                    task.completed
                }

                _viewState.value.copy(
                    incompleteTasks = incomplete,
                    completedTasks = complete,
                    showLoading = false,
                )
            },
            onFailure = {
                _viewState.value.copy(
                    errorMessage = UIText.StringText("Something went wrong."),
                    showLoading = false,
                )
            },
        )
    }

    /**
     * When the done button is clicked, we will render an alert message that states a task has
     * been accomplished, but it provides an undo button to revert this action. We show a temporary
     * state, that indicates the task is done, but we don't actually commit anything to the
     * [taskRepository] until the message is dismissed.
     */
    fun onDoneButtonClicked(task: Task) {
        val taskAccomplishedAlertMessage = AlertMessage(
            message = UIText.ResourceText(R.string.task_accomplished),
            actionText = UIText.ResourceText(R.string.undo),
            onActionClicked = {
                _viewState.update {
                    val taskAsComplete = task.copy(
                        completed = true,
                    )

                    val incompleteTasksToUse = it.incompleteTasks?.plus(task)
                    val completedTasksToUse = it.completedTasks?.minus(taskAsComplete)

                    it.copy(
                        alertMessage = null,
                        incompleteTasks = incompleteTasksToUse,
                        completedTasks = completedTasksToUse,
                    )
                }
            },
            onDismissed = {
                viewModelScope.launch {
                    viewModelScope.launch {
                        val updatedTask = task.copy(
                            completed = true,
                        )

                        taskRepository.updateTask(updatedTask)
                    }

                    _viewState.update {
                        it.copy(
                            alertMessage = null,
                        )
                    }
                }
            },
            duration = AlertMessage.Duration.LONG,
        )

        _viewState.update {
            val taskAsComplete = task.copy(
                completed = true,
            )

            val incompleteTaskToUse = it.incompleteTasks?.minus(task)
            val completedTasksToUse = it.completedTasks?.plus(taskAsComplete)

            it.copy(
                incompleteTasks = incompleteTaskToUse,
                completedTasks = completedTasksToUse,
                alertMessage = taskAccomplishedAlertMessage,
            )
        }
    }

    fun onDateSelected(newDate: LocalDate) {
        _viewState.value = _viewState.value.copy(
            selectedDate = newDate,
        )
    }

    fun onRescheduleButtonClicked(task: Task) {
        _viewState.update {
            it.copy(
                taskToReschedule = task,
            )
        }
    }

    /**
     * When a task is rescheduled, we will render an alert message that states a task has
     * been rescheduled, but it provides an undo button to revert this action. We show a temporary
     * state, that indicates the task is rescheduled, but we don't actually commit anything to the
     * [rescheduleTaskUseCase] until the message is dismissed.
     */
    fun onTaskRescheduled(
        task: Task,
        newDate: LocalDate,
    ) {
        if (newDate < LocalDate.now()) {
            _viewState.update {
                it.copy(
                    taskToReschedule = null,
                    alertMessage = AlertMessage(
                        message = UIText.ResourceText(
                            R.string.err_scheduled_date_in_past,
                        ),
                    ),
                )
            }

            return
        }

        val taskRescheduledAlertMessage = AlertMessage(
            message = UIText.ResourceText(R.string.task_rescheduled),
            actionText = UIText.ResourceText(R.string.undo),
            onActionClicked = {
                _viewState.update {
                    val updatedTasks = it.incompleteTasks?.plus(task)

                    it.copy(
                        alertMessage = null,
                        incompleteTasks = updatedTasks,
                    )
                }
            },
            onDismissed = {
                viewModelScope.launch {
                    rescheduleTaskUseCase.invoke(task, newDate)

                    _viewState.update {
                        it.copy(
                            taskToReschedule = null,
                            alertMessage = null,
                        )
                    }
                }
            },
            duration = AlertMessage.Duration.LONG,
        )

        _viewState.update {
            val tempTasks = it.incompleteTasks?.minus(task)

            it.copy(
                taskToReschedule = null,
                incompleteTasks = tempTasks,
                alertMessage = taskRescheduledAlertMessage,
            )
        }
    }

    fun onReschedulingCompleted() {
        _viewState.update {
            it.copy(
                taskToReschedule = null,
            )
        }
    }

    fun onAlertMessageShown() {
        _viewState.update {
            it.copy(
                alertMessage = null,
            )
        }
    }
}
