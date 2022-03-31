package com.adammcneilly.toa.tasklist.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.fakes.FakeGetTasksForDateUseCase
import com.adammcneilly.toa.fakes.FakeMarkTaskAsCompleteUseCase
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class TaskListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigateToAddTask() {
        val getTasksForDateUseCase = FakeGetTasksForDateUseCase()
        val markTaskAsCompleteUseCase = FakeMarkTaskAsCompleteUseCase()

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now(),
            result = flowOf(Result.Success(emptyList())),
        )

        val viewModel = TaskListViewModel(
            getTasksForDateUseCase = getTasksForDateUseCase,
            markTaskAsCompleteUseCase = markTaskAsCompleteUseCase,
        )

        composeTestRule.setContent {
            TaskListScreen(
                navigator = EmptyDestinationsNavigator,
                viewModel = viewModel,
            )
        }
    }
}
