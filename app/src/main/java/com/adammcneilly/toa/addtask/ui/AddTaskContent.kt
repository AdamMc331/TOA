package com.adammcneilly.toa.addtask.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.R
import com.adammcneilly.toa.addtask.domain.model.TaskInput
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.core.ui.components.Material3CircularProgressIndicator
import com.adammcneilly.toa.core.ui.components.PrimaryButton
import com.adammcneilly.toa.core.ui.components.TOADatePicker
import com.adammcneilly.toa.core.ui.components.TOATextField
import com.adammcneilly.toa.core.ui.components.VerticalSpacer
import com.adammcneilly.toa.core.ui.getString
import com.adammcneilly.toa.core.ui.theme.TOATheme
import java.time.LocalDate

const val ADD_TASK_DESCRIPTION_INPUT_TAG = "ADD_TASK_DESCRIPTION_INPUT"

@Composable
fun AddTaskContent(
    viewState: AddTaskViewState,
    onTaskDescriptionChanged: (String) -> Unit,
    onTaskScheduledDateChanged: (LocalDate) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val descriptionFocusRequester = remember { FocusRequester() }

    // When the add task screen is first created, we want to focus on the description input
    // so the user can begin typing right away.
    LaunchedEffect(Unit) {
        descriptionFocusRequester.requestFocus()
    }

    Box(
        modifier = modifier,
    ) {
        AddTaskInputsColumn(
            viewState = viewState,
            onTaskDescriptionChanged = onTaskDescriptionChanged,
            onTaskScheduledDateChanged = onTaskScheduledDateChanged,
            onSubmitClicked = onSubmitClicked,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            descriptionFocusRequester = descriptionFocusRequester,
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

@Composable
private fun AddTaskInputsColumn(
    viewState: AddTaskViewState,
    onTaskDescriptionChanged: (String) -> Unit,
    onTaskScheduledDateChanged: (LocalDate) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier,
    descriptionFocusRequester: FocusRequester,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TaskDescriptionLabel()

        TaskDescriptionInput(
            text = viewState.taskInput.description,
            onTextChanged = onTaskDescriptionChanged,
            enabled = viewState.inputsEnabled,
            errorMessage = (viewState as? AddTaskViewState.Active)
                ?.descriptionInputErrorMessage
                ?.getString(),
            focusRequester = descriptionFocusRequester,
        )

        TaskDateLabel()

        TaskDateInput(
            value = viewState.taskInput.scheduledDate,
            onValueChanged = onTaskScheduledDateChanged,
            enabled = viewState.inputsEnabled,
            errorMessage = (viewState as? AddTaskViewState.Active)
                ?.scheduledDateInputErrorMessage
                ?.getString(),
        )

        if (viewState is AddTaskViewState.SubmissionError) {
            Text(
                text = viewState.errorMessage.getString(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(top = 12.dp),
            )
        }

        VerticalSpacer(height = dimensionResource(id = R.dimen.form_spacing))

        SubmitButton(
            onClick = onSubmitClicked,
            enabled = viewState.inputsEnabled,
            text = viewState.submitButtonText,
        )
    }
}

@Composable
private fun SubmitButton(
    onClick: () -> Unit,
    enabled: Boolean,
    text: UIText,
) {
    PrimaryButton(
        text = text.getString(),
        onClick = onClick,
        enabled = enabled,
    )
}

@Composable
private fun TaskDateInput(
    value: LocalDate,
    onValueChanged: (LocalDate) -> Unit,
    enabled: Boolean,
    errorMessage: String?,
) {
    TOADatePicker(
        value = value,
        onValueChanged = onValueChanged,
        modifier = Modifier
            .fillMaxWidth(),
        errorMessage = errorMessage,
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
    errorMessage: String?,
    focusRequester: FocusRequester,
) {
    TOATextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = null,
        enabled = enabled,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
        ),
        placeholderText = stringResource(R.string.task_input_placeholder),
        errorMessage = errorMessage,
        focusRequester = focusRequester,
        modifier = Modifier
            .testTag(ADD_TASK_DESCRIPTION_INPUT_TAG)
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
@ExcludeFromJacocoGeneratedReport
private fun AddTaskContentPreview(
    @PreviewParameter(AddTaskViewStateProvider::class)
    addTaskViewState: AddTaskViewState,
) {
    TOATheme {
        Surface {
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
}

class AddTaskViewStateProvider : PreviewParameterProvider<AddTaskViewState> {

    override val values: Sequence<AddTaskViewState>
        get() {
            val activeInput = TaskInput(
                description = "Buy an NFT",
                scheduledDate = LocalDate.now(),
            )

            return sequenceOf(
                AddTaskViewState.Initial(),
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
