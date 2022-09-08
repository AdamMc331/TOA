package com.adammcneilly.toa.login.domain.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.adammcneilly.toa.DataStoreToken
import com.adammcneilly.toa.login.domain.model.AuthToken
import com.adammcneilly.toa.login.domain.model.RefreshToken
import com.adammcneilly.toa.login.domain.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This is a sample [TokenRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing sake.
 */
class DataStoreTokenRepository @Inject constructor(
    private val tokenDataStore: DataStore<DataStoreToken>,
) : TokenRepository {
    override suspend fun storeToken(token: Token) {
        tokenDataStore.updateData { dataStoreToken ->
            dataStoreToken
                .toBuilder()
                .setAuthToken(token.authToken.value)
                .setRefreshToken(token.refreshToken.value)
                .build()
        }
    }

    override suspend fun clearToken() {
        tokenDataStore.updateData { dataStoreToken ->
            dataStoreToken.defaultInstanceForType
        }
    }

    override fun observeToken(): Flow<Token?> {
        return tokenDataStore.data
            .map { dataStoreToken ->
                Log.d("TokenRepository", "Mapped token: ${dataStoreToken.toToken()}")
                dataStoreToken.toToken()
            }
    }
}

private fun DataStoreToken.toToken(): Token? {
    return if (this == DataStoreToken.getDefaultInstance()) {
        null
    } else {
        Token(
            authToken = AuthToken(this.authToken),
            refreshToken = RefreshToken(this.refreshToken),
        )
    }
}
