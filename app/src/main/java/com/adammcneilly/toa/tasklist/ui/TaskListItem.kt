package com.adammcneilly.toa.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.components.TOATextButton
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.tasklist.domain.model.Task

/**
 * This displays a list item for a given [task].
 */
@Composable
fun TaskListItem(
    task: Task,
    onRescheduleClicked: () -> Unit,
    onDoneClicked: () -> Unit,
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.End,
        ) {
            TaskText(
                task.description,
            )

            ButtonRow(
                onRescheduleClicked = onRescheduleClicked,
                onDoneClicked = onDoneClicked,
            )
        }
    }
}

@Composable
private fun ButtonRow(
    onRescheduleClicked: () -> Unit,
    onDoneClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RescheduleButton(onRescheduleClicked)

        DoneButton(onDoneClicked)
    }
}

@Composable
private fun RescheduleButton(
    onClick: () -> Unit,
) {
    TOATextButton(
        text = stringResource(R.string.reschedule),
        onClick = onClick,
    )
}

@Composable
private fun DoneButton(
    onClick: () -> Unit,
) {
    TOATextButton(
        text = stringResource(R.string.done),
        onClick = onClick,
    )
}

@Composable
private fun TaskText(
    text: String,
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
private fun TaskListItemPreview() {
    val task = Task(
        description = "Clean my office space.",
    )

    TOATheme {
        TaskListItem(
            task = task,
            onRescheduleClicked = {},
            onDoneClicked = {},
        )
    }
}
