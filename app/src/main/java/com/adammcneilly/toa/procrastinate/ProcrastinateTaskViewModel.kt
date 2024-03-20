package com.adammcneilly.toa.procrastinate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.destinations.ProcrastinateTaskDialogDestination
import com.adammcneilly.toa.tasklist.domain.usecases.RescheduleTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProcrastinateTaskViewModel @Inject constructor(
    private val rescheduleTaskUseCase: RescheduleTaskUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val args: ProcrastinateTaskNavArgs = ProcrastinateTaskDialogDestination.argsFrom(savedStateHandle)

    private val mutableState = MutableStateFlow(ProcrastinateTaskViewState())
    val state = mutableState.asStateFlow()

    fun optionSelected(
        option: ProcrastinateOption,
    ) {
        mutableState.update { currentState ->
            currentState.copy(
                selectedOption = option,
            )
        }
    }

    fun procrastinateClicked() {
        viewModelScope.launch {
            rescheduleTaskUseCase.invoke(
                task = args.task,
                newDate = state.value.selectedOption.date,
            )
        }

        mutableState.update { currentState ->
            currentState.copy(
                isComplete = true,
            )
        }
    }

    fun futureDateChanged(
        newDate: LocalDate,
    ) {
        mutableState.update { currentState ->
            currentState.copy(
                futureDate = newDate,
            )
        }
    }
}
