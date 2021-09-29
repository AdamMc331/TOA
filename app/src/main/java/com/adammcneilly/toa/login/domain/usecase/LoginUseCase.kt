package com.adammcneilly.toa.login.domain.usecase

import com.adammcneilly.toa.login.domain.model.LoginResult

@JvmInline
value class Email(private val email: String)

@JvmInline
value class Password(private val password: String)

/**
 * This use case consumes any information required to log in the user, and attempts to do so.
 */
interface LoginUseCase {

    suspend operator fun invoke(
        email: Email,
        password: Password,
    ): LoginResult
}
