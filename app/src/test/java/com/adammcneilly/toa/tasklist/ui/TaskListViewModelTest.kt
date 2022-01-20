package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
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
            scheduledDate = LocalDate.now(),
        )

        val taskList = listOf(task)

        val taskResponse = Result.Success(
            taskList,
        )

        testRobot
            .mockAllTasksResult(taskResponse)
            .buildViewModel()
            .assertViewState(
                expectedViewState = TaskListViewState(
                    tasks = taskList,
                    showLoading = false,
                ),
            )
    }

    @Test
    fun failureLoad() {
        val taskResult: Result<List<Task>> = Result.Error(Throwable("Whoops"))

        testRobot
            .mockAllTasksResult(taskResult)
            .buildViewModel()
            .assertViewState(
                expectedViewState = TaskListViewState(
                    errorMessage = UIText.StringText("Something went wrong."),
                    showLoading = false,
                )
            )
    }
}
