package com.adammcneilly.toa.login.domain.usecase

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.InvalidCredentialsException
import com.adammcneilly.toa.login.domain.model.LoginResult
import com.adammcneilly.toa.login.domain.repository.LoginRepository

/**
 * A concrete implementation of a [CredentialsLoginUseCase] that will request logging in
 * via the [loginRepository].
 */
class ProdCredentialsLoginUseCase(
    private val loginRepository: LoginRepository,
) : CredentialsLoginUseCase {

    override suspend fun invoke(credentials: Credentials): LoginResult {
        val repoResult = loginRepository.login(credentials)

        return when (repoResult) {
            is Result.Success -> {
                return LoginResult.Success
            }
            is Result.Error -> {
                loginResultForError(repoResult)
            }
        }
    }

    /**
     * Checks the possible error scenarios for the [repoResult] and maps to an appropriate
     * [LoginResult.Failure].
     */
    private fun loginResultForError(repoResult: Result.Error): LoginResult.Failure {
        return when (repoResult.error) {
            is InvalidCredentialsException -> {
                LoginResult.Failure.InvalidCredentials
            }
            else -> {
                LoginResult.Failure.Unknown
            }
        }
    }
}
