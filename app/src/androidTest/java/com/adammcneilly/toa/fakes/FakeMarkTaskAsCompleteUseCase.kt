package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.tasklist.domain.usecases.MarkTaskAsCompleteUseCase

class FakeMarkTaskAsCompleteUseCase : MarkTaskAsCompleteUseCase {

    override suspend fun invoke(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }
}
