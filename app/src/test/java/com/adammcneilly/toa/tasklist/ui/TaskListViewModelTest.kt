package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.core.ui.UIText
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class TaskListViewModelTest {
    private val testRobot = TaskListViewModelRobot()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun successfulLoad() {
        val task = Task(
            id = "Test",
            description = "Test task",
            scheduledDateMillis = 0L,
            completed = false,
        )

        val taskList = listOf(task)

        val taskResponse = Result.Success(
            taskList,
        )

        testRobot
            .mockTasksForDateResult(
                date = LocalDate.now(),
                result = taskResponse,
            )
            .buildViewModel()
            .assertViewState(
                expectedViewState = TaskListViewState(
                    incompleteTasks = taskList,
                    completedTasks = taskList,
                    showLoading = false,
                ),
            )
    }

    @Test
    fun clickPreviousDate() {
        val task = Task(
            id = "Test",
            description = "Test task",
            scheduledDateMillis = 0L,
            completed = false,
        )

        val taskList = listOf(task)

        val taskListResult = Result.Success(
            taskList,
        )

        val expectedViewState = TaskListViewState(
            selectedDate = LocalDate.now().minusDays(1),
            incompleteTasks = taskList,
            completedTasks = taskList,
            showLoading = false,
        )

        testRobot
            .mockTasksForDateResult(
                date = LocalDate.now(),
                result = Result.Success(emptyList()),
            )
            .mockTasksForDateResult(
                date = LocalDate.now().minusDays(1),
                result = taskListResult,
            )
            .buildViewModel()
            .clickPreviousDateButton()
            .assertViewState(expectedViewState)
    }

    @Test
    fun clickNextDate() {
        val task = Task(
            id = "Test",
            description = "Test task",
            scheduledDateMillis = 0L,
            completed = false,
        )

        val taskList = listOf(task)

        val taskListResult = Result.Success(
            taskList,
        )

        val expectedViewState = TaskListViewState(
            selectedDate = LocalDate.now().plusDays(1),
            incompleteTasks = taskList,
            completedTasks = taskList,
            showLoading = false,
        )

        testRobot
            .mockTasksForDateResult(
                date = LocalDate.now(),
                result = Result.Success(emptyList()),
            )
            .mockTasksForDateResult(
                date = LocalDate.now().plusDays(1),
                result = taskListResult,
            )
            .buildViewModel()
            .clickNextDateButton()
            .assertViewState(expectedViewState)
    }

    @Test
    fun failureLoad() {
        val taskResult: Result<List<Task>> = Result.Error(Throwable("Whoops"))

        testRobot
            .mockTasksForDateResult(
                date = LocalDate.now(),
                result = taskResult,
            )
            .buildViewModel()
            .assertViewState(
                expectedViewState = TaskListViewState(
                    errorMessage = UIText.StringText("Something went wrong."),
                    showLoading = false,
                )
            )
    }
}
