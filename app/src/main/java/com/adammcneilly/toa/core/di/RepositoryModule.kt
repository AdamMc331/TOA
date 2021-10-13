package com.adammcneilly.toa.core.di

import com.adammcneilly.toa.login.domain.repository.DemoLoginService
import com.adammcneilly.toa.login.domain.repository.DemoTokenService
import com.adammcneilly.toa.login.domain.repository.LoginRepository
import com.adammcneilly.toa.login.domain.repository.TokenRepository
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
        tokenRepository: DemoTokenService,
    ): TokenRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepository: DemoLoginService,
    ): LoginRepository
}
