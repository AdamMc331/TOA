package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.core.ui.AlertMessage
import com.adammcneilly.toa.core.ui.UIText
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class TaskListViewModelTest {
    private val testRobot = TaskListViewModelRobot()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun successfulLoad() {
        val incompleteTask = Task(
            id = "Test",
            description = "Test task",
            scheduledDateMillis = 0L,
            completed = false,
        )

        val completedTask = incompleteTask.copy(
            completed = true,
        )

        val taskList = listOf(incompleteTask, completedTask)

        val taskListResult = Result.success(taskList)

        testRobot
            .mockTaskListResultForDate(
                date = LocalDate.now(),
                result = flowOf(taskListResult),
            )
            .buildViewModel()
            .assertViewState(
                expectedViewState = TaskListViewState(
                    incompleteTasks = listOf(incompleteTask),
                    completedTasks = listOf(completedTask),
                    showLoading = false,
                ),
            )
    }

    @Test
    fun rescheduleTaskTriggersAlertMessage() {
        val incompleteTask = Task(
            id = "Test",
            description = "Test task",
            scheduledDateMillis = 0L,
            completed = false,
        )

        val taskList = listOf(incompleteTask)

        val taskListResult = Result.success(taskList)

        val tomorrow = LocalDate.now().plusDays(1)

        testRobot
            .mockTaskListResultForDate(
                date = LocalDate.now(),
                result = flowOf(taskListResult),
            )
            .buildViewModel()
            .clickRescheduleButton(incompleteTask)
            .assertViewState(
                expectedViewState = TaskListViewState(
                    showLoading = false,
                    incompleteTasks = listOf(incompleteTask),
                    completedTasks = emptyList(),
                    taskToReschedule = incompleteTask,
                )
            )
            .rescheduleTaskForDate(
                task = incompleteTask,
                date = tomorrow,
            )
            .assertViewState(
                expectedViewState = TaskListViewState(
                    showLoading = false,
                    incompleteTasks = emptyList(),
                    completedTasks = emptyList(),
                    taskToReschedule = null,
                    alertMessages = listOf(
                        AlertMessage(
                            message = UIText.ResourceText(R.string.task_rescheduled),
                            actionText = UIText.ResourceText(R.string.undo),
                            duration = AlertMessage.Duration.LONG,
                        ),
                    ),
                )
            )
            .dismissAlertMessage()
            .assertTaskRescheduledForDate(
                task = incompleteTask,
                date = tomorrow,
            )
    }

    @Test
    fun preventReschedulingTaskToPastDate() {
        val incompleteTask = Task(
            id = "Test",
            description = "Test task",
            scheduledDateMillis = 0L,
            completed = false,
        )

        val taskList = listOf(incompleteTask)

        val taskListResult = Result.success(taskList)

        val yesterday = LocalDate.now().minusDays(1)

        val alertMessage = AlertMessage(
            message = UIText.ResourceText(
                R.string.err_scheduled_date_in_past,
            ),
        )

        testRobot
            .mockTaskListResultForDate(
                date = LocalDate.now(),
                result = flowOf(taskListResult),
            )
            .buildViewModel()
            .clickRescheduleButton(incompleteTask)
            .assertViewState(
                expectedViewState = TaskListViewState(
                    showLoading = false,
                    incompleteTasks = listOf(incompleteTask),
                    completedTasks = emptyList(),
                    taskToReschedule = incompleteTask,
                )
            )
            .rescheduleTaskForDate(
                task = incompleteTask,
                date = yesterday,
            )
            .assertViewState(
                expectedViewState = TaskListViewState(
                    showLoading = false,
                    incompleteTasks = listOf(incompleteTask),
                    completedTasks = emptyList(),
                    taskToReschedule = null,
                    alertMessages = listOf(
                        alertMessage,
                    ),
                )
            )
            .showAlertMessage()
            .assertViewState(
                expectedViewState = TaskListViewState(
                    showLoading = false,
                    incompleteTasks = listOf(incompleteTask),
                    completedTasks = emptyList(),
                    taskToReschedule = null,
                    alertMessages = emptyList(),
                )
            )
    }

    @Test
    fun failureLoad() {
        val taskResult: Result<List<Task>> = Result.failure(Throwable("Whoops"))

        testRobot
            .mockTaskListResultForDate(
                date = LocalDate.now(),
                result = flowOf(taskResult),
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
