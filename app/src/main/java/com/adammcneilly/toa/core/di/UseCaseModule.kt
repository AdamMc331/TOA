package com.adammcneilly.toa.core.di

import com.adammcneilly.toa.login.domain.usecase.CredentialsLoginUseCase
import com.adammcneilly.toa.login.domain.usecase.ProdCredentialsLoginUseCase
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
    abstract fun bindCredentialsLoginUseCase(
        credentialsLoginUseCase: ProdCredentialsLoginUseCase,
    ): CredentialsLoginUseCase
}
