package com.adammcneilly.toa.deletetask.domain.usecases

import com.adammcneilly.toa.core.models.Task

/**
 * Given a task, delete any reference of that in the user's task list.
 */
interface DeleteTaskUseCase {

    suspend operator fun invoke(task: Task): Result<Unit>
}
