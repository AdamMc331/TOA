package com.adammcneilly.toa.addtask.domain.model

import java.time.LocalDate

/**
 * This entity corresponds to the information required when creating a task.
 */
data class TaskInput(
    val description: String = "",
    val scheduledDate: LocalDate = LocalDate.now(),
)
