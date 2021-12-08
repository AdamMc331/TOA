package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task

/**
 * Fetches all tasks that the user has created.
 */
interface GetAllTasksUseCase {
    suspend operator fun invoke(): Result<List<Task>>
}
