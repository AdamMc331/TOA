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
 *
 * See [AddTaskContainer] docs for why we suppressed the view model forwarding.
 */
@Destination(
    style = DestinationStyle.Dialog::class,
    navArgsDelegate = AddTaskNavArguments::class,
)
@Composable
@Suppress("ViewModelForwarding")
fun AddTaskDialog(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    AddTaskContainer(
        viewModel = viewModel,
        navigator = navigator,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(dimensionResource(id = R.dimen.screen_padding)),
    )
}
