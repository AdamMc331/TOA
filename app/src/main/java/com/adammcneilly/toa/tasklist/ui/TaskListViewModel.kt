package com.adammcneilly.toa.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.usecases.GetTasksForDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    getTasksForDateUseCase: GetTasksForDateUseCase,
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
                    tasks = null,
                )

                getTasksForDateUseCase.invoke(selectedDate)
            }
            .onEach { result ->
                _viewState.value = getViewStateForTaskListResult(result)
            }
            .launchIn(viewModelScope)
    }

    private fun getViewStateForTaskListResult(result: Result<List<Task>>): TaskListViewState {
        return when (result) {
            is Result.Success -> {
                _viewState.value.copy(
                    tasks = result.data,
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
}
