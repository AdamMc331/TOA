package com.adammcneilly.toa.addtask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.adammcneilly.toa.R
import com.google.accompanist.insets.statusBarsPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AddTaskScreen(
    navigator: DestinationsNavigator,
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    LaunchedEffect(viewState.value) {
        if (viewState.value is AddTaskViewState.Completed) {
            navigator.popBackStack()
        }
    }

    AddTaskContent(
        viewState = viewState.value,
        onTaskDescriptionChanged = viewModel::onTaskDescriptionChanged,
        onTaskScheduledDateChanged = viewModel::onTaskScheduledDateChanged,
        onSubmitClicked = viewModel::onSubmitButtonClicked,
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.screen_padding))
            .statusBarsPadding(),
    )
}
