package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.models.Task
import java.time.LocalDate

/**
 * Consume a task and a new date that we want to schedule that
 * task for. We will modify the task, and save that change in our
 * data layer.
 */
interface RescheduleTaskUseCase {
    suspend operator fun invoke(
        task: Task,
        newDate: LocalDate,
    )
}
