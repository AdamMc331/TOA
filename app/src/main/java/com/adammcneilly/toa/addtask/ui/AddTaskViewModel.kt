package com.adammcneilly.toa.addtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.addtask.domain.usecases.AddTaskUseCase
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
) : ViewModel() {

    private val _viewState: MutableStateFlow<AddTaskViewState> =
        MutableStateFlow(AddTaskViewState.Initial)
    val viewState = _viewState.asStateFlow()

    fun onTaskDescriptionChanged(newDescription: String) {
        val currentInput = _viewState.value.taskInput
        val newInput = currentInput.copy(
            description = newDescription,
        )

        _viewState.value = AddTaskViewState.Active(
            taskInput = newInput,
        )
    }

    fun onTaskScheduledDateChanged(newDate: LocalDate) {
        val currentInput = _viewState.value.taskInput
        val newInput = currentInput.copy(
            scheduledDate = newDate,
        )

        _viewState.value = AddTaskViewState.Active(
            taskInput = newInput,
        )
    }

    fun onSubmitButtonClicked() {
        val taskToCreate = Task(
            description = _viewState.value.taskInput.description,
        )

        viewModelScope.launch {
            val result = addTaskUseCase(taskToCreate)

            _viewState.value = when (result) {
                is Result.Success -> {
                    AddTaskViewState.Completed
                }
                is Result.Error -> {
                    AddTaskViewState.SubmissionError(
                        taskInput = _viewState.value.taskInput,
                        errorMessage = UIText.StringText("Unable to add task."),
                    )
                }
            }
        }
    }
}
