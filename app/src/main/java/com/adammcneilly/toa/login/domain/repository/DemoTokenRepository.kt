package com.adammcneilly.toa.login.domain.repository

import androidx.datastore.core.DataStore
import com.adammcneilly.toa.DataStoreToken
import com.adammcneilly.toa.login.domain.model.Token
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * This is a sample [TokenRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing sake.
 */
class DemoTokenRepository @Inject constructor(
    private val tokenDataStore: DataStore<DataStoreToken>,
) : TokenRepository {
    override suspend fun storeToken(token: Token) {
        // No-Op
    }

    @Suppress("MagicNumber")
    override suspend fun fetchToken(): Token? {
        delay(2_500)

        return null
//        return Token(
//            AuthToken("Test"),
//            RefreshToken("Test"),
//        )
    }
}
