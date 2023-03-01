package com.adammcneilly.toa.preferences

import javax.inject.Inject

/**
 * This class maintains the data contract to read and write
 * specific local preferences for using the TOA application.
 */
class UserPreferences @Inject constructor(
    private val preferences: Preferences,
) {

    suspend fun getPreferredNumTasksPerDay(): Int? {
        return preferences.getInt(
            key = NUM_TASKS_PER_DAY,
            defaultValue = null,
        )
    }

    suspend fun setPreferredNumTasksPerDay(numTasks: Int?) {
        preferences.storeInt(
            key = NUM_TASKS_PER_DAY,
            value = numTasks,
        )
    }

    suspend fun getPreferredNumTasksPerDayEnabled(): Boolean {
        return preferences.getBoolean(
            key = NUM_TASKS_PER_DAY_ENABLED,
            defaultValue = false,
        )
    }

    suspend fun setPrefferedNumTasksPerDayEnabled(enabled: Boolean) {
        preferences.storeBoolean(
            key = NUM_TASKS_PER_DAY_ENABLED,
            value = enabled,
        )
    }

    companion object {
        private const val NUM_TASKS_PER_DAY = "num_tasks_per_day"
        private const val NUM_TASKS_PER_DAY_ENABLED = "num_tasks_per_day_enabled"
    }
}
