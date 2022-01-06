package com.adammcneilly.toa.core.data.local

import app.cash.turbine.test
import com.adammcneilly.toa.Database
import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task
import com.google.common.truth.Truth.assertThat
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.LocalDate

class SQLDelightTaskListRepositoryTest {

    @Test
    fun insertReadTask() = runBlockingTest {
        val testTask = Task(
            id = "123",
            description = "Testing",
            scheduledDate = LocalDate.now(),
        )

        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        val database = Database(driver)

        val repository = SQLDelightTaskListRepository(
            taskQueries = database.taskQueries,
        )

        repository.addTask(testTask)

        repository.fetchAllTasks().test {
            val firstResult = awaitItem()
            val firstTask = (firstResult as Result.Success).data.first()

            assertThat(firstTask.id).isEqualTo("123")
            assertThat(firstTask.description).isEqualTo("Testing")
            assertThat(firstTask.scheduledDate).isEqualTo(LocalDate.now())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
