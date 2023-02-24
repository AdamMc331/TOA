package com.adammcneilly.toa.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This will be our state management container for the settings screen.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsViewState())
    val state = _state.asStateFlow()

    init {
        getInitialPreferences()
    }

    private fun getInitialPreferences() {
        viewModelScope.launch {
            val numTasks = userPreferences.getPreferredNumTasksPerDay()
            val numTasksEnabled = userPreferences.getPreferredNumTasksPerDayEnabled()

            _state.update { currentState ->
                currentState.copy(
                    numTasksPerDay = numTasks,
                    numTasksPreferenceEnabled = numTasksEnabled,
                )
            }
        }
    }

    fun numTasksPerDayChanged(input: String) {
        val numTasks = input
            .filter(Char::isDigit)
            .toIntOrNull()

        // Update preferences
        // And update state
        // Ideally, we update preferences, and the state observes this change.
        viewModelScope.launch {
            userPreferences.setPreferredNumTasksPerDay(numTasks)

            _state.update { currentState ->
                currentState.copy(
                    numTasksPerDay = numTasks,
                )
            }
        }
    }

    fun numTasksPerDayEnabledChanged(enabled: Boolean) {
        // Update preferences
        // And update state
        // Ideally, we update preferences, and the state observes this change.
        viewModelScope.launch {
            userPreferences.setPrefferedNumTasksPerDayEnabled(enabled)

            _state.update { currentState ->
                currentState.copy(
                    numTasksPreferenceEnabled = enabled,
                )
            }
        }
    }
}
