package com.adammcneilly.toa.tasklist.ui

import android.content.res.Configuration
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
import com.adammcneilly.toa.core.ui.components.Material3CircularProgressIndicator
import com.adammcneilly.toa.core.ui.getString
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
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
) {
    Scaffold(
        floatingActionButton = {
            AddTaskButton(
                onClick = onAddButtonClicked,
            )
        },
        topBar = {
            TaskListToolbar(
                onLeftButtonClicked = onPreviousDateButtonClicked,
                onRightButtonClicked = onNextDateButtonClicked,
                title = viewState.selectedDateString.getString(),
            )
        },
    ) { paddingValues ->
        if (viewState.tasks != null) {
            TaskList(
                tasks = viewState.tasks,
                onRescheduleClicked = onRescheduleClicked,
                onDoneClicked = onDoneClicked,
                modifier = Modifier
                    .padding(paddingValues),
            )
        }

        if (viewState.showLoading) {
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
    }
}

@Composable
private fun TaskListToolbar(
    onLeftButtonClicked: () -> Unit,
    onRightButtonClicked: () -> Unit,
    title: String,
) {
    val toolbarHeight = 84.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .statusBarsPadding()
                .height(toolbarHeight),
        ) {
            IconButton(
                onClick = onLeftButtonClicked,
            ) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.view_previous_day_content_description),
                    modifier = Modifier
                        .size(84.dp),
                )
            }

            Text(
                text = title,
                modifier = Modifier
                    .weight(1F),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            IconButton(
                onClick = onRightButtonClicked,
            ) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.view_next_day_content_description),
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
            id = "$index",
            description = "Test task: $index",
            scheduledDate = LocalDate.now(),
            completed = false,
        )
    }

    val viewState = TaskListViewState(
        showLoading = false,
        tasks = tasks,
    )

    TOATheme {
        TaskListContent(
            viewState = viewState,
            onRescheduleClicked = {},
            onDoneClicked = {},
            onAddButtonClicked = {},
            onPreviousDateButtonClicked = {},
            onNextDateButtonClicked = {},
        )
    }
}
