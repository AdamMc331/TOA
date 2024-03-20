package com.adammcneilly.toa.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.adammcneilly.toa.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

/**
 * This destination is very similar to [AddTaskScreen], but will present the form
 * to add a task as a Dialog instead.
 */
@Destination(
    style = DestinationStyle.Dialog::class,
    navArgsDelegate = AddTaskNavArguments::class,
)
@Composable
fun AddTaskDialog(
    navigator: DestinationsNavigator,
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    AddTaskContainer(
        viewModel = viewModel,
        navigator = navigator,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large,
            )
            .padding(dimensionResource(id = R.dimen.screen_padding)),
    )
}
