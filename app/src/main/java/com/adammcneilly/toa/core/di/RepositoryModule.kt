package com.adammcneilly.toa.core.di

import com.adammcneilly.toa.login.domain.repository.DemoLoginRepository
import com.adammcneilly.toa.login.domain.repository.DemoTokenRepository
import com.adammcneilly.toa.login.domain.repository.LoginRepository
import com.adammcneilly.toa.login.domain.repository.TokenRepository
import com.adammcneilly.toa.tasklist.domain.repository.DemoTaskListRepository
import com.adammcneilly.toa.tasklist.domain.repository.TaskListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module is responsible for defining the creation of any repository dependencies used in the
 * application.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: DemoTokenRepository,
    ): TokenRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepository: DemoLoginRepository,
    ): LoginRepository

    @Binds
    abstract fun bindTaskListRepository(
        taskListRepository: DemoTaskListRepository,
    ): TaskListRepository
}
