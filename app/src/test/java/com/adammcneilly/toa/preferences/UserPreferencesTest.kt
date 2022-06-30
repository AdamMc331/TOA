package com.adammcneilly.toa.preferences

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserPreferencesTest {
    private val fakePreferences = com.adammcneilly.toa.fakes.FakePreferences()
    private lateinit var userPreferences: UserPreferences

    @Before
    fun setUp() {
        userPreferences = UserPreferences(
            preferences = fakePreferences,
        )
    }

    @Test
    fun readWriteNumTasksPerDay() = runTest {
        val preferredNumTasks = 3

        userPreferences.setPreferredNumTasksPerDay(preferredNumTasks)
        val storedPref = userPreferences.getPreferredNumTasksPerDay()
        assertThat(storedPref).isEqualTo(preferredNumTasks)
    }

    @Test
    fun getDefaultNumTasksPerDay() = runTest {
        val preferredNumTasks = userPreferences.getPreferredNumTasksPerDay()
        assertThat(preferredNumTasks).isNull()
    }
}
