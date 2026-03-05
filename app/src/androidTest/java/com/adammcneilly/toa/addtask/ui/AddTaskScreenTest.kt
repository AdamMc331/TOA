package com.adammcneilly.toa.addtask.ui

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import com.adammcneilly.toa.fakes.FakeDestinationsNavigator
import com.adammcneilly.toa.fakes.FakePreferences
import com.adammcneilly.toa.fakes.FakeTaskRepository
import com.adammcneilly.toa.preferences.UserPreferences
import dejavu.assertRecompositions
import dejavu.assertStable
import dejavu.createRecompositionTrackingRule
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class AddTaskScreenTest {
    @get:Rule
    val composeTestRule = createRecompositionTrackingRule()

    @Test
    fun inputTaskDescription() {
        val viewModel = AddTaskViewModel(
            taskRepository = FakeTaskRepository(),
            userPreferences = UserPreferences(FakePreferences()),
            savedStateHandle = SavedStateHandle(
                initialState = mapOf(
                    "initialDate" to LocalDate.now(),
                ),
            ),
        )

        composeTestRule.setContent {
            AddTaskScreen(
                viewModel = viewModel,
                navigator = FakeDestinationsNavigator(),
            )
        }

        composeTestRule
            .onNodeWithTag(ADD_TASK_DESCRIPTION_INPUT_TAG)
            .assertStable()

        composeTestRule
            .onNodeWithTag(ADD_TASK_DESCRIPTION_INPUT_TAG)
            .performTextInput("Fix Code")

        composeTestRule
            .onNodeWithTag(ADD_TASK_DESCRIPTION_INPUT_TAG)
            .performTextInput("Fix Code Again")

        composeTestRule
            .onNodeWithTag(ADD_TASK_DESCRIPTION_INPUT_TAG)
            .assertRecompositions(2)
    }
}
