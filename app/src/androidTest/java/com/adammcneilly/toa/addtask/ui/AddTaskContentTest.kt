package com.adammcneilly.toa.addtask.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.adammcneilly.toa.addtask.domain.model.TaskInput
import com.adammcneilly.toa.core.ui.UIText
import org.junit.Rule
import org.junit.Test

class AddTaskContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun renderInvalidInputMessages() {
        val descriptionError = "Description error"
        val scheduledDateError = "Scheduled date error"

        val viewState = AddTaskViewState.Active(
            taskInput = TaskInput(),
            descriptionInputErrorMessage = UIText.StringText(descriptionError),
            scheduledDateInputErrorMessage = UIText.StringText(scheduledDateError),
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
        composeTestRule.onNodeWithText(scheduledDateError).assertIsDisplayed()
    }
}
