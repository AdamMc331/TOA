package com.adammcneilly.toa.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * This will be our state management container for the settings screen.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SettingsViewState())
    val state = _state.asStateFlow()

    init {
        // Pull initial settings
    }

    fun numTasksPerDayChanged(input: String) {
        // Maybe we need error handling if the user enters non numbers
        // Instead: we just don't allow that.
        val numTasks = input.toIntOrNull()

        _state.update { currentState ->
            currentState.copy(
                numTasksPerDay = numTasks,
            )
        }
    }

    fun numTasksPerDayEnabledChanged(enabled: Boolean) {
        _state.update { currentState ->
            currentState.copy(
                numTasksPreferenceEnabled = enabled,
            )
        }
    }
}
