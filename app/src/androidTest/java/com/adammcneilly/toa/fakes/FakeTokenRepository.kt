package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.login.domain.model.Token
import com.adammcneilly.toa.login.domain.repository.TokenRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FakeTokenRepository @Inject constructor() : TokenRepository {
    private var tokenFlow = MutableSharedFlow<Token?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override suspend fun storeToken(token: Token) {
        println("ARM - Emitting token: $token")
        tokenFlow.emit(token)
    }

    override suspend fun clearToken() {
        tokenFlow.emit(null)
    }

    /**
     * NOTE: At initial usage, [tokenFlow] doesn't have a value, and one
     * won't be received until [storeToken] is called.
     */
    override fun observeToken(): Flow<Token?> {
        return tokenFlow
    }
}
