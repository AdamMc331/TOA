package com.adammcneilly.toa.settings

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun SettingsScreen(
    // viewModel: SettingsViewModel = hiltViewModel(),
) {
    // val viewState = viewModel.state.collectAsState()

    SettingsContent(
        // viewState = viewState,
    )
}
