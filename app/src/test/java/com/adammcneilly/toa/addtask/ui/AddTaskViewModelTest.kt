package com.adammcneilly.toa.addtask.ui

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.R
import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.addtask.domain.model.TaskInput
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class AddTaskViewModelTest {
    private val testRobot = AddTaskViewModelRobot()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun submitWithEmptyDescription() {
        val taskToSubmit = Task(
            id = "Testing",
            description = "",
            scheduledDate = LocalDate.now(),
        )

        val useCaseResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = true,
            scheduledDateInPast = false,
        )

        val expectedViewState = AddTaskViewState.Active(
            taskInput = TaskInput(
                description = taskToSubmit.description,
                scheduledDate = taskToSubmit.scheduledDate,
            ),
            descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description),
            scheduledDateInputErrorMessage = null,
        )

        testRobot
            .mockResultForTask(
                task = taskToSubmit,
                result = useCaseResult,
            )
            .buildViewModel()
            .enterDescription(taskToSubmit.description)
            .selectDate(taskToSubmit.scheduledDate)
            .clickSubmit()
            .assertViewState(expectedViewState)
    }

    @Test
    fun submitWithInvalidDate() {
        val taskToSubmit = Task(
            id = "Testing",
            description = "Do something",
            scheduledDate = LocalDate.now().minusDays(1),
        )

        val useCaseResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = false,
            scheduledDateInPast = true,
        )

        val expectedViewState = AddTaskViewState.Active(
            taskInput = TaskInput(
                description = taskToSubmit.description,
                scheduledDate = taskToSubmit.scheduledDate,
            ),
            descriptionInputErrorMessage = null,
            scheduledDateInputErrorMessage = UIText.ResourceText(R.string.err_scheduled_date_in_past),
        )

        testRobot
            .mockResultForTask(
                task = taskToSubmit,
                result = useCaseResult,
            )
            .buildViewModel()
            .enterDescription(taskToSubmit.description)
            .selectDate(taskToSubmit.scheduledDate)
            .clickSubmit()
            .assertViewState(expectedViewState)
    }
}
