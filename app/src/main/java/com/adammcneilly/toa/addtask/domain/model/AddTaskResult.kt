package com.adammcneilly.toa.addtask.domain.model

/**
 * A collection of possible results for an attempt to add a new task.
 *
 * @see [com.adammcneilly.toa.login.domain.model.LoginResult] for similar documentation.
 */
sealed class AddTaskResult {
    object Success : AddTaskResult()

    sealed class Failure : AddTaskResult() {

        data class InvalidInput(
            val emptyDescription: Boolean,
            val scheduledDateInPast: Boolean,
        ) : Failure()

        object Unknown : Failure()
    }
}
