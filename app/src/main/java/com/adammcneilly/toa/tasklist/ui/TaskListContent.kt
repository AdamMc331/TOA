package com.adammcneilly.toa.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    if (viewState is TaskListViewState.Loaded) {
        LoadedTasksContents(
            viewState,
            onAddButtonClicked,
            onRescheduleClicked,
            onDoneClicked,
        )
    }
}

@Composable
private fun LoadedTasksContents(
    viewState: TaskListViewState.Loaded,
    onAddButtonClicked: () -> Unit,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            AddTaskButton(
                onClick = onAddButtonClicked,
            )
        },
        topBar = {
            TaskListToolbar()
        }
    ) { paddingValues ->

        TaskList(
            tasks = viewState.tasks,
            onRescheduleClicked = onRescheduleClicked,
            onDoneClicked = onDoneClicked,
            modifier = Modifier
                .padding(paddingValues),
        )
    }
}

@Composable
private fun TaskListToolbar() {
    val toolbarHeight = 84.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(toolbarHeight),
        ) {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = "TODO:",
                    modifier = Modifier
                        .size(84.dp),
                )
            }

            Text(
                text = "Today",
                modifier = Modifier
                    .weight(1F),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "TODO:",
                    modifier = Modifier
                        .size(84.dp),
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
