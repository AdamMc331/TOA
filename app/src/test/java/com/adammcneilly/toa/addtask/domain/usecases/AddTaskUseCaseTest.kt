package com.adammcneilly.toa.addtask.domain.usecases

import com.adammcneilly.toa.addtask.domain.model.AddTaskResult
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.fakes.FakePreferences
import com.adammcneilly.toa.preferences.UserPreferences
import com.adammcneilly.toa.task.api.test.FakeTaskRepository
import com.adammcneilly.toa.withAll
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class AddTaskUseCaseTest {
    private val fakeTaskRepository = FakeTaskRepository()
    private val userPreferences = UserPreferences(
        preferences = FakePreferences(),
    )

    @Test
    fun submitWithEmptyDescription() =
        runBlockingTest {
            val taskToSubmit = Task(
                id = "Testing",
                description = "",
                scheduledDateMillis = ZonedDateTime.now()
                    .toInstant()
                    .toEpochMilli(),
                completed = false,
            )

            val expectedResult = AddTaskResult.Failure.InvalidInput(
                emptyDescription = true,
                scheduledDateInPast = false,
            )

            val actualResult = withAll(fakeTaskRepository, userPreferences) {
                addTask(
                    task = taskToSubmit,
                    ignoreTaskLimits = false,
                )
            }
            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitWithBlankDescription() =
        runBlockingTest {
            val taskToSubmit = Task(
                id = "Testing",
                description = "         ",
                scheduledDateMillis = ZonedDateTime.now()
                    .toInstant()
                    .toEpochMilli(),
                completed = false,
            )

            val expectedResult = AddTaskResult.Failure.InvalidInput(
                emptyDescription = true,
                scheduledDateInPast = false,
            )

            val actualResult = withAll(fakeTaskRepository, userPreferences) {
                addTask(
                    task = taskToSubmit,
                    ignoreTaskLimits = false,
                )
            }
            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitWithScheduledDateInPast() =
        runBlockingTest {
            val taskToSubmit = Task(
                id = "Testing",
                description = "Some description",
                scheduledDateMillis = LocalDate.now().minusDays(1)
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli(),
                completed = false,
            )

            val expectedResult = AddTaskResult.Failure.InvalidInput(
                emptyDescription = false,
                scheduledDateInPast = true,
            )

            val actualResult = withAll(fakeTaskRepository, userPreferences) {
                addTask(
                    task = taskToSubmit,
                    ignoreTaskLimits = false,
                )
            }
            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitValidTaskWithExtraWhitespace() =
        runTest {
            val inputTask = Task(
                id = "Some ID",
                description = "   Testing      ",
                scheduledDateMillis = ZonedDateTime.now()
                    .toInstant()
                    .toEpochMilli(),
                completed = false,
            )

            val expectedSavedTask = inputTask.copy(
                description = "Testing",
            )

            fakeTaskRepository.addTaskResults[expectedSavedTask] = Result.success(Unit)

            val expectedResult = AddTaskResult.Success
            val actualResult = withAll(fakeTaskRepository, userPreferences) {
                addTask(
                    task = inputTask,
                    ignoreTaskLimits = false,
                )
            }
            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitWithoutPreferenceLimit() =
        runTest {
            val inputTask = Task(
                id = "Some ID",
                description = "   Testing",
                scheduledDateMillis = ZonedDateTime.now()
                    .toInstant()
                    .toEpochMilli(),
                completed = false,
            )

            val expectedSavedTask = inputTask.copy(
                description = "Testing",
            )

            fakeTaskRepository.addTaskResults[expectedSavedTask] = Result.success(Unit)

            val expectedResult = AddTaskResult.Success
            val actualResult = withAll(fakeTaskRepository, userPreferences) {
                addTask(
                    task = inputTask,
                    ignoreTaskLimits = false,
                )
            }
            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitWithPreferenceLimit() =
        runTest {
            val today = ZonedDateTime.now()
                .toInstant()
                .toEpochMilli()

            // For sake of testing, lol
            userPreferences.setPreferredNumTasksPerDay(0)
            userPreferences.setPrefferedNumTasksPerDayEnabled(true)

            // Mock an empty task list for this date.
            fakeTaskRepository.tasksForDateResults[Pair(today, false)] =
                flowOf(Result.success(emptyList()))

            val inputTask = Task(
                id = "Some ID",
                description = "   Testing",
                scheduledDateMillis = today,
                completed = false,
            )

            val expectedResult = AddTaskResult.Failure.MaxTasksPerDayExceeded

            val actualResult = withAll(fakeTaskRepository, userPreferences) {
                addTask(
                    task = inputTask,
                    ignoreTaskLimits = false,
                )
            }
            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitWithIgnoringPreferenceLimit() =
        runTest {
            val today = ZonedDateTime.now()
                .toInstant()
                .toEpochMilli()

            // For sake of testing, lol
            userPreferences.setPreferredNumTasksPerDay(0)

            // Mock an empty task list for this date.
            fakeTaskRepository.tasksForDateResults[Pair(today, false)] =
                flowOf(Result.success(emptyList()))

            val inputTask = Task(
                id = "Some ID",
                description = "   Testing",
                scheduledDateMillis = today,
                completed = false,
            )

            val expectedSavedTask = inputTask.copy(
                description = "Testing",
            )

            fakeTaskRepository.addTaskResults[expectedSavedTask] = Result.success(Unit)

            val expectedResult = AddTaskResult.Success
            val actualResult = withAll(fakeTaskRepository, userPreferences) {
                addTask(
                    task = inputTask,
                    ignoreTaskLimits = true,
                )
            }
            assertThat(actualResult).isEqualTo(expectedResult)
        }

    /**
     * If our preferences say we have a limit on tasks,
     * but the preference is disabled, ensure that we ignore this limit.
     */
    @Test
    fun submitWithPreferenceLimitButDisabled() =
        runTest {
            userPreferences.setPreferredNumTasksPerDay(0)
            userPreferences.setPrefferedNumTasksPerDayEnabled(false)

            val taskToSubmit = Task(
                id = "Testing",
                description = "Test",
                scheduledDateMillis = ZonedDateTime.now()
                    .toInstant()
                    .toEpochMilli(),
                completed = false,
            )

            // Mock a result for this task specifically
            // we do this to ensure in our test that the insert function is ultimately
            // called with the task that we expect it to be.
            fakeTaskRepository.addTaskResults[taskToSubmit] = Result.success(Unit)

            val expectedResult = AddTaskResult.Success

            val actualResult = withAll(fakeTaskRepository, userPreferences) {
                addTask(
                    task = taskToSubmit,
                    ignoreTaskLimits = false,
                )
            }

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}
