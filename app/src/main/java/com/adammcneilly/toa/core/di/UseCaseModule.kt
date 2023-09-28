package com.adammcneilly.toa.core.di

import com.adammcneilly.toa.tasklist.domain.usecases.GetAllTasksUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.GetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.ProdGetAllTasksUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.ProdGetTasksForDateUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.ProdRescheduleTaskUseCase
import com.adammcneilly.toa.tasklist.domain.usecases.RescheduleTaskUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module is responsible for defining the creation of any use case dependencies in the application.
 *
 * NOTE: If this gets very large, we may want to consider refactoring and making modules feature
 * dependent.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetAllTasksUseCase(
        getAllTasksUseCase: ProdGetAllTasksUseCase,
    ): GetAllTasksUseCase

    @Binds
    abstract fun bindAddTaskUseCase(
        addTaskUseCase: ProdAddTaskUseCase,
    ): AddTaskUseCase

    @Binds
    abstract fun bindGetTasksForDateUseCase(
        getTasksForDateUseCase: ProdGetTasksForDateUseCase,
    ): GetTasksForDateUseCase

    @Binds
    abstract fun bindRescheduleTaskUseCase(
        rescheduleTaskUseCase: ProdRescheduleTaskUseCase,
    ): RescheduleTaskUseCase
}
