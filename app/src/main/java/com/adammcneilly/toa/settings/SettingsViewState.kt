package com.adammcneilly.toa.settings

data class SettingsViewState(
    val numTasksPerDay: Int? = null,
    val numTasksPreferenceEnabled: Boolean = false,
)
