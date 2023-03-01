package com.adammcneilly.toa.settings

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.fakes.FakePreferences
import com.adammcneilly.toa.preferences.UserPreferences
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val userPreferences = UserPreferences(
        preferences = FakePreferences(),
    )

    private lateinit var settingsViewModel: SettingsViewModel

    @Test
    fun getInitialPreferences() = runTest {
        // Modify the preferences
        userPreferences.setPrefferedNumTasksPerDayEnabled(true)
        userPreferences.setPreferredNumTasksPerDay(5)

        // Create the VM
        settingsViewModel = SettingsViewModel(userPreferences)

        // Verify state.
        val expectedState = SettingsViewState(
            numTasksPerDay = 5,
            numTasksPreferenceEnabled = true,
        )

        assertThat(settingsViewModel.state.value).isEqualTo(expectedState)
    }

    @Test
    fun preferredNumberOfTasksChanged() = runTest {
        // Create the VM
        settingsViewModel = SettingsViewModel(userPreferences)

        // Make sure our initial state has null tasks per day
        assertThat(settingsViewModel.state.value.numTasksPerDay).isNull()

        // Modify tasks per day
        settingsViewModel.numTasksPerDayChanged("5")

        // Verify both VM and settings changed
        assertThat(settingsViewModel.state.value.numTasksPerDay).isEqualTo(5)
        assertThat(userPreferences.getPreferredNumTasksPerDay()).isEqualTo(5)
    }

    @Test
    fun preferredNumberOfTasksChangedWithExtraCharacters() = runTest {
        // Create the VM
        settingsViewModel = SettingsViewModel(userPreferences)

        // Make sure our initial state has null tasks per day
        assertThat(settingsViewModel.state.value.numTasksPerDay).isNull()

        // Modify tasks per day
        settingsViewModel.numTasksPerDayChanged("-5-")

        // Verify both VM and settings changed
        assertThat(settingsViewModel.state.value.numTasksPerDay).isEqualTo(5)
        assertThat(userPreferences.getPreferredNumTasksPerDay()).isEqualTo(5)
    }

    @Test
    fun preferredNumberOfTasksChangedWithEmptyInput() = runTest {
        // Create the VM
        settingsViewModel = SettingsViewModel(userPreferences)

        // Make sure our initial state has null tasks per day
        assertThat(settingsViewModel.state.value.numTasksPerDay).isNull()

        // Modify tasks per day
        settingsViewModel.numTasksPerDayChanged("")

        // Verify both VM and settings changed
        assertThat(settingsViewModel.state.value.numTasksPerDay).isNull()
        assertThat(userPreferences.getPreferredNumTasksPerDay()).isNull()
    }

    @Test
    fun preferredNumberOfTasksEnabledChanged() = runTest {
        // Create the VM
        settingsViewModel = SettingsViewModel(userPreferences)

        // Make sure our initial state has false preference enabled
        assertThat(settingsViewModel.state.value.numTasksPreferenceEnabled).isFalse()

        // Modify enabled state
        settingsViewModel.numTasksPerDayEnabledChanged(true)

        // Verify both VM and settings changed
        assertThat(settingsViewModel.state.value.numTasksPreferenceEnabled).isTrue()
        assertThat(userPreferences.getPreferredNumTasksPerDayEnabled()).isTrue()
    }
}
