package com.adammcneilly.toa.login.domain.model

@JvmInline
value class AuthToken(val value: String)

@JvmInline
value class RefreshToken(val value: String)

/**
 * Contains the information necessary for authenticating network requests.
 *
 * @property[authToken] The current token used to validate a user's request.
 * @property[refreshToken] A token used to generate a new [authToken] if the current one is expired.
 */
data class Token(
    val authToken: AuthToken,
    val refreshToken: RefreshToken,
)
