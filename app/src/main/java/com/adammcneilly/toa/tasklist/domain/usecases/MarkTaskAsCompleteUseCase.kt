package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task

/**
 * Given a task, mark it as completed so it's no longer shown in the todo list.
 */
interface MarkTaskAsCompleteUseCase {

    suspend operator fun invoke(task: Task): Result<Unit>
}
