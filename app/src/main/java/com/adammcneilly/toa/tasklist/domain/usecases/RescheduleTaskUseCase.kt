package com.adammcneilly.toa.tasklist.domain.usecases

interface RescheduleTaskUseCase {

    suspend operator fun invoke(taskId: String): Result<Unit>
}
