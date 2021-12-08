package com.adammcneilly.toa.addtask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.adammcneilly.toa.R
import com.google.accompanist.insets.statusBarsPadding
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    AddTaskContent(
        viewState = viewState.value,
        onTaskDescriptionChanged = {},
        onTaskScheduledDateChanged = {},
        onSubmitClicked = {},
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.screen_padding))
            .statusBarsPadding(),
    )
}
