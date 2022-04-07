package com.adammcneilly.toa.tasklist.ui

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.core.ui.adaptiveWidth
import com.adammcneilly.toa.core.ui.components.Material3CircularProgressIndicator
import com.adammcneilly.toa.core.ui.getString
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListContent(
    viewState: TaskListViewState,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
    onAddButtonClicked: () -> Unit,
    onPreviousDateButtonClicked: () -> Unit,
    onNextDateButtonClicked: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onTaskRescheduled: (Task, LocalDate) -> Unit,
    onReschedulingCompleted: () -> Unit,
    onAlertMessageShown: () -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    TaskListSnackbar(
        alertMessage = viewState.alertMessage,
        snackbarHostState = snackbarHostState,
        onAlertMessageShown = onAlertMessageShown,
    )

    Scaffold(
        floatingActionButton = {
            AddTaskButton(
                onClick = onAddButtonClicked,
            )
        },
        topBar = {
            ToolbarAndDialog(
                viewState,
                onDateSelected,
                onPreviousDateButtonClicked,
                onNextDateButtonClicked,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        if (viewState.showTasks) {
            if (
                viewState.incompleteTasks.isNullOrEmpty() &&
                viewState.completedTasks.isNullOrEmpty()
            ) {
                TaskListEmptyState()
            } else {
                RescheduleTaskDialog(viewState, onTaskRescheduled, onReschedulingCompleted)

                TaskList(
                    incompleteTasks = viewState.incompleteTasks.orEmpty(),
                    completedTasks = viewState.completedTasks.orEmpty(),
                    onRescheduleClicked = onRescheduleClicked,
                    onDoneClicked = onDoneClicked,
                    modifier = Modifier
                        .padding(paddingValues)
                        .adaptiveWidth(),
                )
            }
        }

        if (viewState.showLoading) {
            TaskListLoadingContent()
        }
    }
}

@Composable
private fun TaskListLoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Material3CircularProgressIndicator(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
        )
    }
}

@Composable
private fun TaskListSnackbar(
    alertMessage: UIText?,
    snackbarHostState: SnackbarHostState,
    onAlertMessageShown: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    if (alertMessage != null) {
        val message = alertMessage.getString()

        LaunchedEffect(snackbarHostState) {
            coroutineScope.launch {
                val snackbarResult = snackbarHostState.showSnackbar(message = message)

                when (snackbarResult) {
                    SnackbarResult.Dismissed -> {
                        onAlertMessageShown.invoke()
                    }
                    SnackbarResult.ActionPerformed -> TODO()
                }
            }
        }
    }
}

@Composable
private fun RescheduleTaskDialog(
    viewState: TaskListViewState,
    onTaskRescheduled: (Task, LocalDate) -> Unit,
    onDismissed: () -> Unit,
) {
    val rescheduleTaskDatePickerDialogState = rememberMaterialDialogState()

    LaunchedEffect(viewState) {
        if (viewState.taskToReschedule != null) {
            rescheduleTaskDatePickerDialogState.show()
        }
    }

    MaterialDialog(
        dialogState = rescheduleTaskDatePickerDialogState,
        buttons = {
            positiveButton(stringResource(R.string.ok))
            negativeButton(
                text = stringResource(R.string.cancel),
                onClick = onDismissed,
            )
        },
        onCloseRequest = { dialogState ->
            onDismissed.invoke()
            dialogState.hide()
        }
    ) {
        this.datepicker(
            initialDate = viewState.selectedDate,
            onDateChange = { newDate ->
                if (viewState.taskToReschedule != null) {
                    onTaskRescheduled.invoke(
                        viewState.taskToReschedule,
                        newDate,
                    )
                }
            },
        )
    }
}

@Composable
private fun ToolbarAndDialog(
    viewState: TaskListViewState,
    onDateSelected: (LocalDate) -> Unit,
    onPreviousDateButtonClicked: () -> Unit,
    onNextDateButtonClicked: () -> Unit,
) {
    val dialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(R.string.ok))
            negativeButton(stringResource(R.string.cancel))
        },
    ) {
        this.datepicker(
            initialDate = viewState.selectedDate,
            onDateChange = onDateSelected,
        )
    }

    TaskListToolbar(
        onLeftButtonClicked = onPreviousDateButtonClicked,
        onRightButtonClicked = onNextDateButtonClicked,
        title = viewState.selectedDateString.getString(),
        onTitleClicked = {
            dialogState.show()
        },
    )
}

@Composable
private fun TaskListEmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.no_tasks_scheduled_label),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.Center)
                .adaptiveWidth(),
        )
    }
}

@Composable
private fun TaskListToolbar(
    onLeftButtonClicked: () -> Unit,
    onRightButtonClicked: () -> Unit,
    title: String,
    onTitleClicked: () -> Unit,
) {
    val toolbarHeight = 84.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .statusBarsPadding()
                .height(toolbarHeight)
                .adaptiveWidth(),
        ) {
            IconButton(
                onClick = onLeftButtonClicked,
            ) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.view_previous_day_content_description),
                    modifier = Modifier
                        .size(toolbarHeight),
                )
            }

            Crossfade(
                targetState = title,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable(
                        onClick = onTitleClicked,
                    )
                    .weight(1F)
                    .height(toolbarHeight),
            ) { title ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }

            IconButton(
                onClick = onRightButtonClicked,
            ) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.view_next_day_content_description),
                    modifier = Modifier
                        .size(toolbarHeight),
                )
            }
        }
    }
}

@Composable
private fun AddTaskButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .navigationBarsPadding(),
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.add_task),
        )
    }
}

@Suppress("MagicNumber")
class TaskListViewStateProvider : PreviewParameterProvider<TaskListViewState> {

    override val values: Sequence<TaskListViewState>
        get() {
            val incompleteTasks = (1..3).map { index ->
                Task(
                    id = "INCOMPLETE_TASK_$index",
                    description = "Test task: $index",
                    scheduledDateMillis = 0L,
                    completed = false,
                )
            }

            val completedTasks = (1..3).map { index ->
                Task(
                    id = "COMPLETED_TASK_$index",
                    description = "Test task: $index",
                    scheduledDateMillis = 0L,
                    completed = true,
                )
            }

            val loadingState = TaskListViewState(
                showLoading = true,
            )

            val taskListState = TaskListViewState(
                showLoading = false,
                incompleteTasks = incompleteTasks,
                completedTasks = completedTasks,
            )

            val emptyState = TaskListViewState(
                showLoading = false,
                incompleteTasks = emptyList(),
                completedTasks = emptyList(),
            )

            val errorState = TaskListViewState(
                showLoading = false,
                errorMessage = UIText.StringText("Something went wrong"),
            )

            return sequenceOf(
                loadingState,
                taskListState,
                emptyState,
                errorState,
            )
        }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Medium",
    widthDp = 700,
)
@Preview(
    name = "Expanded",
    widthDp = 840,
)
@Composable
@Suppress("UnusedPrivateMember")
@ExcludeFromJacocoGeneratedReport
private fun TaskListContentPreview(
    @PreviewParameter(TaskListViewStateProvider::class)
    viewState: TaskListViewState,
) {
    TOATheme {
        TaskListContent(
            viewState = viewState,
            onRescheduleClicked = {},
            onDoneClicked = {},
            onAddButtonClicked = {},
            onPreviousDateButtonClicked = {},
            onNextDateButtonClicked = {},
            onDateSelected = {},
            onTaskRescheduled = { _, _ ->
            },
            onReschedulingCompleted = {},
            onAlertMessageShown = {},
        )
    }
}
