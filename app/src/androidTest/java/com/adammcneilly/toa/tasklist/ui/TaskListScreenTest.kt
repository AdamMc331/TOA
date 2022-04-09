package com.adammcneilly.toa.tasklist.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.WindowSize
import com.adammcneilly.toa.destinations.AddTaskDialogDestination
import com.adammcneilly.toa.destinations.AddTaskScreenDestination
import com.adammcneilly.toa.fakes.FakeDestinationsNavigator
import com.adammcneilly.toa.fakes.FakeGetTasksForDateUseCase
import com.adammcneilly.toa.fakes.FakeMarkTaskAsCompleteUseCase
import com.adammcneilly.toa.fakes.FakeRescheduleTaskUseCase
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class TaskListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigateToAddTaskForCompactWindowSize() {
        val getTasksForDateUseCase = FakeGetTasksForDateUseCase()
        val markTaskAsCompleteUseCase = FakeMarkTaskAsCompleteUseCase()
        val rescheduleTaskUseCase = FakeRescheduleTaskUseCase()

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now(),
            result = flowOf(Result.Success(emptyList())),
        )

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now().plusDays(1),
            result = flowOf(Result.Success(emptyList())),
        )

        val viewModel = TaskListViewModel(
            getTasksForDateUseCase = getTasksForDateUseCase,
            markTaskAsCompleteUseCase = markTaskAsCompleteUseCase,
            rescheduleTaskUseCase = rescheduleTaskUseCase,
        )

        val destinationsNavigator = FakeDestinationsNavigator()

        composeTestRule.setContent {
            TaskListScreen(
                navigator = destinationsNavigator,
                viewModel = viewModel,
                windowSize = WindowSize.Compact,
            )
        }

        composeTestRule
            .onNodeWithTag(NEXT_DAY_BUTTON_TAG)
            .performClick()

        composeTestRule
            .onNodeWithTag(ADD_TASK_BUTTON_TAG)
            .performClick()

        destinationsNavigator.verifyNavigatedToDirection(
            expectedDirection = AddTaskScreenDestination(
                initialDate = LocalDate.now().plusDays(1),
            )
        )
    }

    @Test
    fun navigateToAddTaskForExpandedWindowSize() {
        val getTasksForDateUseCase = FakeGetTasksForDateUseCase()
        val markTaskAsCompleteUseCase = FakeMarkTaskAsCompleteUseCase()
        val rescheduleTaskUseCase = FakeRescheduleTaskUseCase()

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now(),
            result = flowOf(Result.Success(emptyList())),
        )

        val viewModel = TaskListViewModel(
            getTasksForDateUseCase = getTasksForDateUseCase,
            markTaskAsCompleteUseCase = markTaskAsCompleteUseCase,
            rescheduleTaskUseCase = rescheduleTaskUseCase,
        )

        val destinationsNavigator = FakeDestinationsNavigator()

        composeTestRule.setContent {
            TaskListScreen(
                navigator = destinationsNavigator,
                viewModel = viewModel,
                windowSize = WindowSize.Expanded,
            )
        }

        composeTestRule
            .onNodeWithTag(ADD_TASK_BUTTON_TAG)
            .performClick()

        destinationsNavigator.verifyNavigatedToDirection(
            expectedDirection = AddTaskDialogDestination(
                initialDate = LocalDate.now(),
            )
        )
    }
}
