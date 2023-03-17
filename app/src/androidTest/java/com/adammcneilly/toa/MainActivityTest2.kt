package com.adammcneilly.toa

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.adammcneilly.toa.core.data.local.TOADatabase
import com.adammcneilly.toa.tasklist.ui.ADD_TASK_BUTTON_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@HiltAndroidTest
class MainActivityTest2 {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: TOADatabase

    @Before
    fun setUp() {
        hiltRule.inject()

        database.clearAllTables()
    }

    @Test
    fun verifyEmptyTaskListShown() {
        composeTestRule
            .onNodeWithText(
                text = composeTestRule.activity.getString(R.string.no_tasks_scheduled_label),
            )
            .assertIsDisplayed()
    }

    @Test
    fun addTask() {
        // Verify empty label shown
        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.no_tasks_scheduled_label))
            .assertIsDisplayed()

        // Click add task button
        composeTestRule
            .onNodeWithTag(ADD_TASK_BUTTON_TAG)
            .performClick()

        // Enter a task description
        composeTestRule
            .onNodeWithText("Clean my office space...")
            .performTextInput("My Task Name")

        // Click submit
        composeTestRule
            .onNodeWithText("SUBMIT")
            .performClick()

        // Verify task name appears on list screen.
        composeTestRule
            .onNodeWithText("My Task Name")
            .assertIsDisplayed()
    }
}
