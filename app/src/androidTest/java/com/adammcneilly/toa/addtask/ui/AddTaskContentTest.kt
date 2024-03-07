package com.adammcneilly.toa.addtask.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.adammcneilly.toa.addtask.domain.model.TaskInput
import com.adammcneilly.toa.core.ui.UIText
import org.junit.Rule
import org.junit.Test

class AddTaskContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun renderInitialViewState() {
        val viewState = AddTaskViewState.Initial()

        composeTestRule.setContent {
            AddTaskContent(
                viewState = viewState,
                onTaskDescriptionChanged = {},
                onTaskScheduledDateChanged = {},
                onSubmitClicked = {},
            )
        }

        composeTestRule
            .onNodeWithTag(ADD_TASK_DESCRIPTION_INPUT_TAG)
            .assertIsFocused()
    }

    @Test
    fun renderInvalidInputMessages() {
        val descriptionError = "Description error"

        val viewState = AddTaskViewState.Active(
            taskInput = TaskInput(),
            descriptionInputErrorMessage = UIText.StringText(descriptionError),
        )

        composeTestRule.setContent {
            AddTaskContent(
                viewState = viewState,
                onTaskDescriptionChanged = {},
                onTaskScheduledDateChanged = {},
                onSubmitClicked = {},
            )
        }

        composeTestRule.onNodeWithText(descriptionError).assertIsDisplayed()
    }
}
