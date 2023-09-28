package com.adammcneilly.toa.addtask.ui

import com.adammcneilly.toa.CoroutinesTestRule
import com.adammcneilly.toa.R
import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.addtask.domain.model.TaskInput
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.core.ui.UIText
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class AddTaskViewModelTest {
    private val testRobot = AddTaskViewModelRobot()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun createWithInitialDateFromSavedStateHandle() {
        val initialDate = LocalDate.now().plusDays(1)

        val expectedViewState = AddTaskViewState.Initial(
            initialDate = initialDate,
        )

        testRobot
            .mockInitialDate(initialDate)
            .buildViewModel()
            .assertViewState(expectedViewState)
    }

    @Test
    fun submitWithEmptyDescription() {
        val taskToSubmit = Task(
            id = "Testing",
            description = "",
            scheduledDateMillis = 0L,
            completed = false,
        )

        val expectedViewState = AddTaskViewState.Active(
            taskInput = TaskInput(
                description = taskToSubmit.description,
                scheduledDate = Instant.ofEpochMilli(taskToSubmit.scheduledDateMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(),
            ),
            descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description),
        )

        testRobot
            .mockInitialDate(LocalDate.now())
            .buildViewModel()
            .enterDescription(taskToSubmit.description)
            .selectDate(taskToSubmit.scheduledDateMillis)
            .clickSubmit()
            .assertViewState(expectedViewState)
    }
}
