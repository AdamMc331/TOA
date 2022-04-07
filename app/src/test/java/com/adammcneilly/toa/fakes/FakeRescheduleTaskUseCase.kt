package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.tasklist.domain.usecases.RescheduleTaskUseCase
import java.time.LocalDate

class FakeRescheduleTaskUseCase : RescheduleTaskUseCase {

    override suspend fun invoke(task: Task, newDate: LocalDate) {
        // No-op
    }
}
