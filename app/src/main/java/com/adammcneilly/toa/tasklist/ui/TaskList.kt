package com.adammcneilly.toa.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.components.Material3Card
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.tasklist.domain.model.Task
import java.time.LocalDate

@Composable
fun TaskList(
    incompleteTasks: List<Task>,
    completedTasks: List<Task>,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.list_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.list_padding)),
        modifier = modifier,
    ) {
        item {
            SectionHeader(text = stringResource(R.string.incomplete_tasks_header))
        }

        if (incompleteTasks.isEmpty()) {
            item {
                EmptySectionCard(
                    text = stringResource(R.string.no_incomplete_tasks_label),
                )
            }
        } else {
            items(incompleteTasks) { task ->
                TaskListItem(
                    task = task,
                    onRescheduleClicked = {
                        onRescheduleClicked(task)
                    },
                    onDoneClicked = {
                        onDoneClicked(task)
                    },
                )
            }
        }

        item {
            SectionHeader(text = stringResource(R.string.completed_tasks_header))
        }

        if (completedTasks.isEmpty()) {
            item {
                EmptySectionCard(
                    text = stringResource(R.string.no_completed_tasks_label),
                )
            }
        } else {
            items(completedTasks) { task ->
                TaskListItem(
                    task = task,
                    onRescheduleClicked = {
                        onRescheduleClicked(task)
                    },
                    onDoneClicked = {
                        onDoneClicked(task)
                    },
                )
            }
        }
    }
}

@Composable
private fun EmptySectionCard(
    text: String,
) {
    Material3Card {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    vertical = 32.dp,
                    horizontal = 24.dp,
                ),
        )
    }
}

@Composable
private fun SectionHeader(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
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
private fun TaskListPreview() {
    val incompleteTasks = (1..5).map { index ->
        Task(
            id = "$index",
            description = "Test task: $index",
            scheduledDate = LocalDate.now(),
            completed = false,
        )
    }

    val completedTasks = (1..5).map { index ->
        Task(
            id = "$index",
            description = "Test task: $index",
            scheduledDate = LocalDate.now(),
            completed = true,
        )
    }

    TOATheme {
        TaskList(
            incompleteTasks = incompleteTasks,
            completedTasks = completedTasks,
            onRescheduleClicked = {},
            onDoneClicked = {},
        )
    }
}
