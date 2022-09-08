package com.adammcneilly.toa.login.domain.repository

import com.adammcneilly.toa.login.domain.model.Token
import kotlinx.coroutines.flow.Flow

/**
 * This repository is responsible for fetching and storing a user's authentication token.
 */
interface TokenRepository {

    /**
     * Given an [token], store this somewhere so it can be retrieved later.
     */
    suspend fun storeToken(
        token: Token,
    )

    /**
     * Clear any cached authentication token.
     */
    suspend fun clearToken()

    /**
     * Fetches the token of the signed is user, if we have one saved.
     *
     * @return The token or null if not found.
     */
    fun observeToken(): Flow<Token?>
}
