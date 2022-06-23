package com.adammcneilly.toa.login.domain.repository

import com.adammcneilly.toa.login.domain.model.AuthToken
import com.adammcneilly.toa.login.domain.model.RefreshToken
import com.adammcneilly.toa.login.domain.model.Token
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * This is a sample [TokenRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing sake.
 */
class DemoTokenRepository @Inject constructor() : TokenRepository {
    override suspend fun storeToken(token: Token) {
        // No-Op
    }

    override suspend fun fetchToken(): Token? {
        delay(5_000)

//        return null
        return Token(
            AuthToken("Test"),
            RefreshToken("Test"),
        )
    }
}
