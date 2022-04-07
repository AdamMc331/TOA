package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.tasklist.domain.usecases.RescheduleTaskUseCase
import com.google.common.truth.Truth.assertThat
import java.time.LocalDate

class FakeRescheduleTaskUseCase : RescheduleTaskUseCase {

    private val invocations: MutableList<Pair<Task, LocalDate>> = mutableListOf()

    override suspend fun invoke(task: Task, newDate: LocalDate) {
        invocations.add(
            Pair(task, newDate),
        )
    }

    fun assertInvocation(
        invocation: Pair<Task, LocalDate>
    ) {
        assertThat(invocations).contains(invocation)
    }
}
