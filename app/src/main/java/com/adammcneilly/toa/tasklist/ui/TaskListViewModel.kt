package com.adammcneilly.toa.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.adammcneilly.toa.tasklist.domain.usecases.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    getAllTasksUseCase: GetAllTasksUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<TaskListViewState> =
        MutableStateFlow(TaskListViewState.Loading)

    val viewState: StateFlow<TaskListViewState> = _viewState

    init {
        getAllTasksUseCase()
            .onEach { result ->
                _viewState.value = getViewStateForTaskListResult(result)
            }
            .launchIn(viewModelScope)
    }

    private fun getViewStateForTaskListResult(result: Result<List<Task>>): TaskListViewState {
        return when (result) {
            is Result.Success -> {
                TaskListViewState.Loaded(
                    tasks = result.data,
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
