package com.adammcneilly.toa.tasklist.domain.usecases

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task

interface GetAllTasksUseCase {
    suspend operator fun invoke(): Result<List<Task>>
}
