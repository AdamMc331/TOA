package com.adammcneilly.toa.core.models

import java.io.Serializable

/**
 * @property[scheduledDateMillis] The time, in milliseconds, to the date that this task is scheduled for.
 */
data class Task(
    val id: String,
    val description: String,
    val scheduledDateMillis: Long,
    val completed: Boolean,
) : Serializable
