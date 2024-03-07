package com.adammcneilly.toa.di

import com.adammcneilly.toa.core.data.local.RoomTaskRepository
import com.adammcneilly.toa.core.di.RepositoryModule
import com.adammcneilly.toa.login.domain.repository.DataStoreTokenRepository
import com.adammcneilly.toa.login.domain.repository.DemoLoginRepository
import com.adammcneilly.toa.login.domain.repository.LoginRepository
import com.adammcneilly.toa.login.domain.repository.TokenRepository
import com.adammcneilly.toa.task.api.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * At the time I'm writing this comment, this module is not used, but we kept it here for education
 * sake, as this shows how we can write a module to override dependencies set inside the application
 * variant.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
abstract class FakeRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: DataStoreTokenRepository,
    ): TokenRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepository: DemoLoginRepository,
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindTaskListRepository(
        taskListRepository: RoomTaskRepository,
    ): TaskRepository
}
