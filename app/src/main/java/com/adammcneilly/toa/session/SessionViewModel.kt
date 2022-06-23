package com.adammcneilly.toa.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.login.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
) : ViewModel() {
    private val _sessionState: MutableStateFlow<SessionState> = MutableStateFlow(SessionState.UNINITIALIZED)
    val sessionState = _sessionState.asStateFlow()

    init {
        viewModelScope.launch {
            getInitialSessionState()
        }
    }

    /**
     * While we have the ability to observe our logged in status,
     * in this instance we just want to retrieve the current status, so that we can
     * determine how to render our MainActivity initially.
     */
    private suspend fun getInitialSessionState() {
        val isLoggedIn = tokenRepository.observeToken().first() != null

        val newSessionState = if (isLoggedIn) {
            SessionState.LOGGED_IN
        } else {
            SessionState.LOGGED_OUT
        }

        _sessionState.update {
            newSessionState
        }
    }
}
