package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import kotlinx.coroutines.flow.Flow

/**
 * Fetches all tasks that the user has created.
 */
interface GetAllTasksUseCase {
    operator fun invoke(): Flow<Result<List<com.adammcneilly.toa.core.models.Task>>>
}
