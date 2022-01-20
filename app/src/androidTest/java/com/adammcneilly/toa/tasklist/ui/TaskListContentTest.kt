package com.adammcneilly.toa.tasklist.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.adammcneilly.toa.R
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

class TaskListContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

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
            )
        }

        val nextButtonContentDescription = composeTestRule.activity
            .getString(R.string.view_next_day_content_description)

        composeTestRule
            .onNodeWithContentDescription(nextButtonContentDescription)
            .performClick()

        assertThat(hasClickedNextDate).isTrue()
    }
}
