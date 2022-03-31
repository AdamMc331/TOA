package com.adammcneilly.toa.addtask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.adammcneilly.toa.R
import com.google.accompanist.insets.statusBarsPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * This destination is used, when we want to show an individual screen that allows
 * the user to create a new task.
 */
@Destination
@Composable
// Update this to consume an initial date
fun AddTaskScreen(
    navigator: DestinationsNavigator,
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    AddTaskContainer(
        viewModel = viewModel,
        navigator = navigator,
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.screen_padding))
            .statusBarsPadding()
    )
}
