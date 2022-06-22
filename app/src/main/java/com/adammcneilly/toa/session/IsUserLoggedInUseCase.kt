package com.adammcneilly.toa.session

import com.adammcneilly.toa.login.domain.repository.TokenRepository

/**
 * A use case to extract the domain logic to check if a user is currently signed in
 * on this device.
 */
class IsUserLoggedInUseCase(
    private val tokenRepository: TokenRepository
) {

    suspend fun isUserLoggedIn(): Boolean {
        val authToken = tokenRepository.fetchToken()
        return authToken != null
    }
}
