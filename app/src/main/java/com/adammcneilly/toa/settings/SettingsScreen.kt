package com.adammcneilly.toa.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val viewState = viewModel.state.collectAsState()

    SettingsContent(
        viewState = viewState.value,
        onNumTasksChanged = viewModel::numTasksPerDayChanged,
        onNumTasksEnabledChanged = viewModel::numTasksPerDayEnabledChanged,
    )
}
