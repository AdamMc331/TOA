package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.tasklist.domain.model.Task
import java.time.format.DateTimeFormatter

/**
 * Creates a user friendly representation of a [Task] that shows a brief summary of what needs
 * to be done.
 */
data class TaskDisplayModel(
    val taskId: String = "",
    val description: String,
    val scheduledDate: String,
)

fun Task.toDisplayModel(): TaskDisplayModel {
    val friendlyDatePattern = "MMM dd, yyyy"
    val friendlyDateFormatter = DateTimeFormatter.ofPattern(friendlyDatePattern)

    return TaskDisplayModel(
        description = this.description,
        scheduledDate = friendlyDateFormatter.format(this.scheduledDate),
    )
}
