package com.adammcneilly.toa.ui.login

/**
 * This defines the state of the login screen.
 *
 * @param[userName] The current text entered in the username field.
 * @param[password] The current text entered in the password field.
 */
data class LoginViewState(
    val userName: String,
    val password: String,
)
