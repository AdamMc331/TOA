package com.adammcneilly.toa.login.ui

/**
 * This defines the state of the login screen.
 *
 * @param[email] The current text entered in the email field.
 * @param[password] The current text entered in the password field.
 */
data class LoginViewState(
    val email: String,
    val password: String,
)
