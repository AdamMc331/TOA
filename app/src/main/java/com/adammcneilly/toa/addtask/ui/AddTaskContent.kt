package com.adammcneilly.toa.addtask.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.adammcneilly.toa.R
import com.adammcneilly.toa.addtask.domain.model.TaskInput
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.core.ui.components.Material3CircularProgressIndicator
import com.adammcneilly.toa.core.ui.components.PrimaryButton
import com.adammcneilly.toa.core.ui.components.TOATextField
import com.adammcneilly.toa.core.ui.components.VerticalSpacer
import com.adammcneilly.toa.core.ui.theme.TOATheme
import java.time.LocalDate

@Composable
fun AddTaskContent(
    viewState: AddTaskViewState,
    onTaskDescriptionChanged: (String) -> Unit,
    onTaskScheduledDateChanged: (LocalDate) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = modifier,
        ) {
            AddTaskInputsColumn(
                viewState = viewState,
                onTaskDescriptionChanged = onTaskDescriptionChanged,
                onSubmitClicked = onSubmitClicked,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            if (viewState is AddTaskViewState.Submitting) {
                Material3CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun AddTaskInputsColumn(
    viewState: AddTaskViewState,
    onTaskDescriptionChanged: (String) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing)),
    ) {
        TaskDescriptionLabel()

        TaskDescriptionInput(
            text = viewState.taskInput.description,
            onTextChanged = onTaskDescriptionChanged,
            enabled = viewState.inputsEnabled,
        )

        TaskDateLabel()

        TaskDateInput(
            enabled = viewState.inputsEnabled,
        )

        VerticalSpacer(height = dimensionResource(id = R.dimen.form_spacing))

        SubmitButton(
            onClick = onSubmitClicked,
            enabled = viewState.inputsEnabled,
        )
    }
}

@Composable
private fun SubmitButton(
    onClick: () -> Unit,
    enabled: Boolean,
) {
    PrimaryButton(
        text = "Submit",
        onClick = onClick,
        enabled = enabled,
    )
}

@Composable
private fun TaskDateInput(
    enabled: Boolean,
) {
    TOATextField(
        text = "Today",
        onTextChanged = {},
        labelText = "",
        enabled = enabled,
    )
}

@Composable
private fun TaskDateLabel() {
    Text(
        text = "When would you like to do it?",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun TaskDescriptionInput(
    text: String,
    onTextChanged: (String) -> Unit,
    enabled: Boolean,
) {
    TOATextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = "",
        enabled = enabled,
    )
}

@Composable
private fun TaskDescriptionLabel() {
    Text(
        text = "What would you like to accomplish?",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
    )
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun AddTaskContentPreview(
    @PreviewParameter(AddTaskViewStateProvider::class)
    addTaskViewState: AddTaskViewState,
) {
    TOATheme {
        AddTaskContent(
            viewState = addTaskViewState,
            onTaskDescriptionChanged = {},
            onTaskScheduledDateChanged = {},
            onSubmitClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.screen_padding)),
        )
    }
}

class AddTaskViewStateProvider : PreviewParameterProvider<AddTaskViewState> {

    override val values: Sequence<AddTaskViewState>
        get() {
            val activeInput = TaskInput(
                description = "Buy an NFT",
                scheduledDate = LocalDate.now(),
            )

            return sequenceOf(
                AddTaskViewState.Initial,
                AddTaskViewState.Active(
                    taskInput = activeInput,
                ),
                AddTaskViewState.Submitting(
                    taskInput = activeInput,
                ),
                AddTaskViewState.SubmissionError(
                    taskInput = activeInput,
                    errorMessage = UIText.StringText("Don't buy NFTs."),
                ),
                AddTaskViewState.Completed,
            )
        }
}
