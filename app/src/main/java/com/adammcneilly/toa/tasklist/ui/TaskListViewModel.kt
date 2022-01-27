package com.adammcneilly.toa.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.usecases.GetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.MarkTaskAsCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasksForDateUseCase: GetTasksForDateUseCase,
    private val markTaskAsCompleteUseCase: MarkTaskAsCompleteUseCase,
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
                clearTasksAndShowLoading()

                val incompleteTaskFlow = getTasksForDateUseCase.invoke(
                    date = selectedDate,
                    completed = false,
                )

                val completedTaskFlow = getTasksForDateUseCase.invoke(
                    date = selectedDate,
                    completed = true,
                )

                incompleteTaskFlow.combine(completedTaskFlow) { incompleteTaskListResult, completedTaskListResult ->
                    (incompleteTaskListResult to completedTaskListResult)
                }
            }
            .onEach { (incompleteTaskListResult, completedTaskListResult) ->
                _viewState.value = getViewStateForTaskListResults(
                    incompleteTaskListResult = incompleteTaskListResult,
                    completedTaskListResult = completedTaskListResult,
                )
            }
            .launchIn(viewModelScope)
    }

    private fun clearTasksAndShowLoading() {
        _viewState.value = _viewState.value.copy(
            showLoading = true,
            incompleteTasks = null,
            completedTasks = null,
        )
    }

    private fun getViewStateForTaskListResults(
        incompleteTaskListResult: Result<List<Task>>,
        completedTaskListResult: Result<List<Task>>,
    ): TaskListViewState {
        return when {
            incompleteTaskListResult is Result.Success &&
                completedTaskListResult is Result.Success -> {
                _viewState.value.copy(
                    incompleteTasks = incompleteTaskListResult.data,
                    completedTasks = completedTaskListResult.data,
                    showLoading = false,
                )
            }
            else -> {
                _viewState.value.copy(
                    errorMessage = UIText.StringText("Something went wrong."),
                    showLoading = false,
                )
            }
        }
    }

    fun onPreviousDateButtonClicked() {
        _viewState.value = _viewState.value.copy(
            selectedDate = _viewState.value.selectedDate.minusDays(1),
        )
    }

    fun onNextDateButtonClicked() {
        _viewState.value = _viewState.value.copy(
            selectedDate = _viewState.value.selectedDate.plusDays(1),
        )
    }

    fun onDoneButtonClicked(task: Task) {
        viewModelScope.launch {
            markTaskAsCompleteUseCase.invoke(task)
        }
    }
}
