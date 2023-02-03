package com.adammcneilly.toa.login.domain.usecase

import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.InvalidCredentialsException
import com.adammcneilly.toa.login.domain.model.LoginResult
import com.adammcneilly.toa.login.domain.repository.LoginRepository
import com.adammcneilly.toa.login.domain.repository.TokenRepository
import javax.inject.Inject

/**
 * The [ProdCredentialsLoginUseCase] is used to login a user with a given set of credentials.
 * We will login via the supplied [loginRepository], and store the retrieved auth information inside
 * our [tokenRepository].
 */
class ProdCredentialsLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository,
) {

    suspend fun login(credentials: Credentials): LoginResult {
        val validationResult = validateCredentials(credentials)

        if (validationResult != null) {
            return validationResult
        }

        val repoResult = loginRepository.login(credentials)

        return repoResult.fold(
            onSuccess = { loginResponse ->
                tokenRepository.storeToken(loginResponse.token)
                LoginResult.Success
            },
            onFailure = { error ->
                loginResultForError(error)
            },
        )
    }

    private fun validateCredentials(credentials: Credentials): LoginResult.Failure.EmptyCredentials? {
        val emptyEmail = credentials.email.value.isEmpty()
        val emptyPassword = credentials.password.value.isEmpty()

        return if (emptyEmail || emptyPassword) {
            LoginResult.Failure.EmptyCredentials(
                emptyEmail = emptyEmail,
                emptyPassword = emptyPassword,
            )
        } else {
            null
        }
    }

    /**
     * Checks the possible error scenarios for the [error] and maps to an appropriate
     * [LoginResult.Failure].
     */
    private fun loginResultForError(error: Throwable): LoginResult.Failure {
        return when (error) {
            is InvalidCredentialsException -> {
                LoginResult.Failure.InvalidCredentials
            }
            else -> {
                LoginResult.Failure.Unknown
            }
        }
    }
}
