package com.adammcneilly.toa.addtask.ui

import androidx.lifecycle.SavedStateHandle
import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.fakes.FakePreferences
import com.adammcneilly.toa.preferences.UserPreferences
import com.adammcneilly.toa.task.api.test.FakeTaskRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class AddTaskViewModelRobot {
    private val fakeTaskRepository = FakeTaskRepository()
    private val fakeUserPreferences = UserPreferences(FakePreferences())
    private val mockSavedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private lateinit var viewModel: AddTaskViewModel

    fun buildViewModel() = apply {
        viewModel = AddTaskViewModel(
            taskRepository = fakeTaskRepository,
            userPreferences = fakeUserPreferences,
            savedStateHandle = mockSavedStateHandle,
        )
    }

    fun mockInitialDate(
        date: LocalDate,
    ) = apply {
        every {
            mockSavedStateHandle.get<LocalDate?>("initialDate")
        } returns date
    }

    /**
     * NOTE: Since we're no longer depending on use case as its own type,
     * we need to split this up into two functions: one mocks a response from the taskrepository
     * and one mocks the response from the user preferences
     */
    fun mockResultForTask(
        task: Task,
        result: AddTaskResult
    ) = apply {
        TODO()
//        fakeTaskRepository.addTaskResults[task] = result
    }

    fun enterDescription(
        newDescription: String,
    ) = apply {
        viewModel.onTaskDescriptionChanged(newDescription)
    }

    fun selectDate(
        newScheduledDate: Long,
    ) = apply {
        val scheduledDate = Instant.ofEpochMilli(newScheduledDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        viewModel.onTaskScheduledDateChanged(scheduledDate)
    }

    fun clickSubmit() = apply {
        viewModel.onSubmitButtonClicked()
    }

    fun assertViewState(
        expectedViewState: AddTaskViewState,
    ) = apply {
        val actualViewState = viewModel.viewState.value
        assertThat(actualViewState).isEqualTo(expectedViewState)
    }
}
