package com.adammcneilly.toa.tasklist.ui

import android.content.res.Configuration
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.google.accompanist.insets.ui.Scaffold

@Composable
fun TaskListContent(
    viewState: TaskListViewState,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
    onAddButtonClicked: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            if (viewState is TaskListViewState.Loaded) {
                AddTaskButton(
                    onClick = onAddButtonClicked,
                )
            }
        }
    ) {
        if (viewState is TaskListViewState.Loaded) {
            TaskList(
                tasks = viewState.tasks,
                onRescheduleClicked = onRescheduleClicked,
                onDoneClicked = onDoneClicked,
            )
        }
    }
}

@Composable
private fun AddTaskButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.add_task),
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
@Composable
@Suppress("UnusedPrivateMember")
private fun TaskListContentPreview() {
    val tasks = (1..10).map { index ->
        Task(
            description = "Test task: $index",
        )
    }

    val viewState = TaskListViewState.Loaded(tasks)

    TOATheme {
        TaskListContent(
            viewState = viewState,
            onRescheduleClicked = {},
            onDoneClicked = {},
            onAddButtonClicked = {},
        )
    }
}
