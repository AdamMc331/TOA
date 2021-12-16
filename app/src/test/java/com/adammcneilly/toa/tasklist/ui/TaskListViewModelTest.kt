package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import org.junit.Rule
import org.junit.Test

class TaskListViewModelTest {
    private val testRobot = TaskListViewModelRobot()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun successfulLoad() {
        val task = Task(
            description = "Test task",
        )

        val taskList = listOf(task)

        val taskResponse = Result.Success(
            taskList,
        )

        val expectedTaskList = listOf(
            TaskDisplayModel(
                task.description,
                scheduledDate = "Today",
                onDoneClicked = {},
                onRescheduledClicked = {},
            )
        )

        testRobot
            .mockAllTasksResult(taskResponse)
            .buildViewModel()
            .assertViewState(
                expectedViewState = TaskListViewState.Loaded(expectedTaskList),
            )
    }

    @Test
    fun failureLoad() {
        val taskResult: Result<List<Task>> = Result.Error(Throwable("Whoops"))

        testRobot
            .mockAllTasksResult(taskResult)
            .buildViewModel()
            .assertViewState(
                expectedViewState = TaskListViewState.Error(
                    errorMessage = UIText.StringText("Something went wrong.")
                )
            )
    }
}
