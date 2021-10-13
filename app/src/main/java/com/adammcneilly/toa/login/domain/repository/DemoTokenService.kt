package com.adammcneilly.toa.login.domain.repository

import com.adammcneilly.toa.login.domain.model.Token

/**
 * This is a sample [TokenRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing sake.
 */
class DemoTokenService : TokenRepository {
    override suspend fun storeToken(token: Token) {
        // No-Op
    }

    override suspend fun fetchToken(): Token? {
        return null
    }
}
