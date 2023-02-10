package com.adammcneilly.toa.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
) : ViewModel() {
    private val _sessionState: MutableStateFlow<SessionState> = MutableStateFlow(SessionState.UNINITIALIZED)
    val sessionState = _sessionState.asStateFlow()

    init {
        getSessionStateFromLoggedInStatus()
    }

    /**
     * Observe the logged in status, update the MainActivity any time it changes.
     */
    private fun getSessionStateFromLoggedInStatus() {
        println("ARM - session viewmodel created")
        isUserLoggedInUseCase
            .isUserLoggedIn()
            .distinctUntilChanged()
            .onEach { isLoggedIn ->
                println("ARM - Observing logged in state: $isLoggedIn")

                val newSessionState = if (isLoggedIn) {
                    SessionState.LOGGED_IN
                } else {
                    SessionState.LOGGED_OUT
                }

                _sessionState.update {
                    newSessionState
                }
            }
            .launchIn(viewModelScope)
    }
}
