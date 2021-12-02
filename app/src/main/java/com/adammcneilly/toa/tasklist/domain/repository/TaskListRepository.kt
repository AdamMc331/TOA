package com.adammcneilly.toa.tasklist.domain.repository

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.tasklist.domain.model.Task

interface TaskListRepository {

    suspend fun fetchAllTasks(): Result<List<Task>>
}
