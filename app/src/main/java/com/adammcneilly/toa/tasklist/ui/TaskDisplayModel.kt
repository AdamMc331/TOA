package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.tasklist.domain.model.Task

/**
 * Creates a user friendly representation of a [Task] that shows a brief summary of what needs
 * to be done.
 */
data class TaskDisplayModel(
    val description: String,
    val scheduledDate: String,
    val onRescheduledClicked: () -> Unit,
    val onDoneClicked: () -> Unit,
)
