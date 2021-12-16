package com.adammcneilly.toa.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.usecases.GetAllTasksUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.RescheduleTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val rescheduleTaskUseCase: RescheduleTaskUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<TaskListViewState> =
        MutableStateFlow(TaskListViewState.Loading)

    val viewState: StateFlow<TaskListViewState> = _viewState

    init {
        viewModelScope.launch {
            val getTasksResult = getAllTasksUseCase()

            _viewState.value = when (getTasksResult) {
                is Result.Success -> {
                    val displayModels = getTasksResult.data.map { task ->
                        mapToDisplayModel(task)
                    }

                    TaskListViewState.Loaded(
                        tasks = displayModels,
                    )
                }
                is Result.Error -> {
                    TaskListViewState.Error(
                        errorMessage = UIText.StringText("Something went wrong.")
                    )
                }
            }
        }
    }

    private fun mapToDisplayModel(task: Task): TaskDisplayModel {
        val friendlyDatePattern = "MMM dd, yyyy"
        val friendlyDateFormatter = DateTimeFormatter.ofPattern(friendlyDatePattern)

        return TaskDisplayModel(
            description = task.description,
            scheduledDate = friendlyDateFormatter.format(task.scheduledDate),
            onRescheduledClicked = {
                viewModelScope.launch {
                    rescheduleTaskUseCase(task.id)
                }
            },
            onDoneClicked = {
                // Coming soon
            }
        )
    }
}
