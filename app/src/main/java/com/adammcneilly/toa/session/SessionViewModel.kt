package com.adammcneilly.toa.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adammcneilly.toa.login.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
            getSessionStateFromLoggedInStatus()
        }
    }

    private fun getSessionStateFromLoggedInStatus() {
        tokenRepository
            .observeToken()
            .map { token ->
                if (token == null) {
                    SessionState.LOGGED_OUT
                } else {
                    SessionState.LOGGED_IN
                }
            }
            .onEach { newSessionState ->
                _sessionState.update {
                    newSessionState
                }
            }
            .launchIn(viewModelScope)
    }
}
