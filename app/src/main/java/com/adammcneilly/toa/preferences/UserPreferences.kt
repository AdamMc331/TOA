package com.adammcneilly.toa.preferences

/**
 * This class maintains the data contract to read and write
 * specific local preferences for using the TOA application.
 */
class UserPreferences(
    private val preferences: Preferences,
) {

    suspend fun getPreferredNumTasksPerDay(): Int? {
        return preferences.getInt(
            key = NUM_TASKS_PER_DAY,
            defaultValue = null,
        )
    }

    suspend fun setPreferredNumTasksPerDay(numTasks: Int) {
        preferences.storeInt(
            key = NUM_TASKS_PER_DAY,
            value = numTasks,
        )
    }

    companion object {
        private const val NUM_TASKS_PER_DAY = "num_tasks_per_day"
    }
}
