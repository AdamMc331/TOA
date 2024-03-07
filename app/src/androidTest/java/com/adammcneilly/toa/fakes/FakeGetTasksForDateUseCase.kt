package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.task.api.TaskListResult
import com.adammcneilly.toa.tasklist.domain.usecases.GetTasksForDateUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class FakeGetTasksForDateUseCase : GetTasksForDateUseCase {
    private val resultsForDateMap: MutableMap<LocalDate, Flow<TaskListResult>> = mutableMapOf()

    fun mockResultForDate(
        date: LocalDate,
        result: Flow<TaskListResult>,
    ) {
        resultsForDateMap[date] = result
    }

    override fun invoke(
        date: LocalDate,
    ): Flow<TaskListResult> {
        return resultsForDateMap[date]!!
    }
}
