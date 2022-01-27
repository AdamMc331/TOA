package com.adammcneilly.toa.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.usecases.GetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.MarkTaskAsCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
                _viewState.value = _viewState.value.copy(
                    showLoading = true,
                    incompleteTasks = null,
                )

                getTasksForDateUseCase.invoke(
                    date = selectedDate,
                    appendCompletedTasks = true,
                )
            }
            .onEach { result ->
                _viewState.value = getViewStateForIncompleteTaskListResult(result)
            }
            .launchIn(viewModelScope)
    }

    private fun getViewStateForIncompleteTaskListResult(result: Result<List<Task>>): TaskListViewState {
        return when (result) {
            is Result.Success -> {
                val (completedTasks, incompleteTasks) = result.data.partition { it.completed }
                _viewState.value.copy(
                    completedTasks = completedTasks,
                    incompleteTasks = incompleteTasks,
                    showLoading = false,
                )
            }

            is Result.Error -> {
                _viewState.value.copy(
                    errorMessage = UIText.StringText("Something went wrong."),
                    showLoading = false,
                )
            }
        }
    }

    private fun getViewStateForCompletedTaskListResult(result: Result<List<Task>>): TaskListViewState {
        return when (result) {
            is Result.Success -> {
                _viewState.value.copy(
                    completedTasks = result.data,
                    showLoading = false,
                )
            }

            is Result.Error -> {
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
