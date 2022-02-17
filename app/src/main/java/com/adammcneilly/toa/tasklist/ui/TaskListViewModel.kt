package com.adammcneilly.toa.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.usecases.GetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.MarkTaskAsCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        observeIncompleteTasksForSelectedDate()
        observeCompletedTasksForSelectedDate()
    }

    private fun observeIncompleteTasksForSelectedDate() {
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
                    completed = false,
                )
            }
            .onEach { result ->
                _viewState.value = getViewStateForIncompleteTaskListResult(result)
            }
            .launchIn(viewModelScope)
    }

    private fun observeCompletedTasksForSelectedDate() {
        _viewState
            .map { viewState ->
                viewState.selectedDate
            }
            .distinctUntilChanged()
            .flatMapLatest { selectedDate ->
                _viewState.value = _viewState.value.copy(
                    showLoading = true,
                    completedTasks = null,
                )

                getTasksForDateUseCase.invoke(
                    date = selectedDate,
                    completed = true,
                )
            }
            .onEach { result ->
                _viewState.value = getViewStateForCompletedTaskListResult(result)
            }
            .launchIn(viewModelScope)
    }

    private fun getViewStateForIncompleteTaskListResult(result: Result<List<com.adammcneilly.toa.core.models.Task>>): TaskListViewState {
        return when (result) {
            is Result.Success -> {
                _viewState.value.copy(
                    incompleteTasks = result.data,
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

    private fun getViewStateForCompletedTaskListResult(result: Result<List<com.adammcneilly.toa.core.models.Task>>): TaskListViewState {
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

    fun onDoneButtonClicked(task: com.adammcneilly.toa.core.models.Task) {
        viewModelScope.launch {
            markTaskAsCompleteUseCase.invoke(task)
        }
    }
}
