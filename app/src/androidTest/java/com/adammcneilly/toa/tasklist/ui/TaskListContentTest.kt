package com.adammcneilly.toa.tasklist.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.models.Task
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime

class TaskListContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testTask = Task(
        id = "Test ID",
        description = "Test Task",
        scheduledDateMillis = ZonedDateTime.now()
            .toInstant()
            .toEpochMilli(),
        completed = false,
    )

    @Test
    fun clickPreviousDateButton() {
        var hasClickedPreviousDate = false

        composeTestRule.setContent {
            TaskListContent(
                viewState = TaskListViewState(),
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = {},
                onPreviousDateButtonClicked = {
                    hasClickedPreviousDate = true
                },
                onNextDateButtonClicked = {},
                onDateSelected = {},
            )
        }

        val previousButtonContentDescription = composeTestRule.activity
            .getString(R.string.view_previous_day_content_description)

        composeTestRule
            .onNodeWithContentDescription(previousButtonContentDescription)
            .performClick()

        assertThat(hasClickedPreviousDate).isTrue()
    }

    @Test
    fun clickNextDateButton() {
        var hasClickedNextDate = false

        composeTestRule.setContent {
            TaskListContent(
                viewState = TaskListViewState(),
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = {},
                onPreviousDateButtonClicked = {},
                onNextDateButtonClicked = {
                    hasClickedNextDate = true
                },
                onDateSelected = {},
            )
        }

        val nextButtonContentDescription = composeTestRule.activity
            .getString(R.string.view_next_day_content_description)

        composeTestRule
            .onNodeWithContentDescription(nextButtonContentDescription)
            .performClick()

        assertThat(hasClickedNextDate).isTrue()
    }

    @Test
    fun renderWithNoTasks() {
        val viewState = TaskListViewState(
            showLoading = false,
            incompleteTasks = emptyList(),
            completedTasks = emptyList(),
        )

        composeTestRule.setContent {
            TaskListContent(
                viewState = viewState,
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = {},
                onPreviousDateButtonClicked = {},
                onNextDateButtonClicked = {},
                onDateSelected = {},
            )
        }

        val noTasksScheduledLabel = composeTestRule.activity.getString(R.string.no_tasks_scheduled_label)

        composeTestRule
            .onNodeWithText(noTasksScheduledLabel)
            .assertIsDisplayed()
    }

    @Test
    fun renderWithNoIncompleteTasks() {
        val completedTask = testTask.copy(
            completed = true,
        )

        val viewState = TaskListViewState(
            showLoading = false,
            incompleteTasks = emptyList(),
            completedTasks = listOf(completedTask),
        )

        composeTestRule.setContent {
            TaskListContent(
                viewState = viewState,
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = {},
                onPreviousDateButtonClicked = {},
                onNextDateButtonClicked = {},
                onDateSelected = {},
            )
        }

        val noIncompleteTasksLabel = composeTestRule.activity.getString(R.string.no_incomplete_tasks_label)

        composeTestRule
            .onNodeWithText(noIncompleteTasksLabel)
            .assertIsDisplayed()

        val expectedTaskTag = "COMPLETED_TASK_${testTask.id}"

        composeTestRule
            .onNodeWithTag(expectedTaskTag)
            .assertIsDisplayed()
            .assert(!hasAnyChild(hasTestTag("BUTTON_ROW")))
    }

    @Test
    fun renderWithNoCompleteTasks() {
        val incompleteTask = testTask.copy(
            completed = false,
        )

        val viewState = TaskListViewState(
            showLoading = false,
            incompleteTasks = listOf(incompleteTask),
            completedTasks = emptyList(),
        )

        composeTestRule.setContent {
            TaskListContent(
                viewState = viewState,
                onRescheduleClicked = {},
                onDoneClicked = {},
                onAddButtonClicked = {},
                onPreviousDateButtonClicked = {},
                onNextDateButtonClicked = {},
                onDateSelected = {},
            )
        }

        val expectedTaskTag = "INCOMPLETE_TASK_${testTask.id}"

        composeTestRule
            .onNodeWithTag(expectedTaskTag)
            .assertIsDisplayed()
            .assert(hasAnyChild(hasTestTag("BUTTON_ROW")))
    }
}
