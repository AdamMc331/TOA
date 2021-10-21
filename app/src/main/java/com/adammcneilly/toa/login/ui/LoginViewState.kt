package com.adammcneilly.toa.login.ui

import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.login.domain.model.Credentials

/**
 * A sealed class defining all possible states of our login screen.
 *
 * @property[credentials] The current credentials entered by the user.
 * @property[inputsEnabled] If true, the buttons on the login screen can accept clicks,
 * false otherwise.
 */
sealed class LoginViewState(
    open val credentials: Credentials,
    open val inputsEnabled: Boolean = true,
) {
    /**
     * The initial state of the screen with nothing input.
     */
    object Initial : LoginViewState(
        credentials = Credentials(),
    )

    /**
     * The state of the screen as the user is entering email information.
     */
    data class Active(
        override val credentials: Credentials,
        val emailInputErrorMessage: UIText? = null,
        val passwordInputErrorMessage: UIText? = null,
    ) : LoginViewState(
        credentials = credentials,
    )

    /**
     * The state of the screen as the user is attempting to login.
     */
    data class Submitting(
        override val credentials: Credentials,
    ) : LoginViewState(
        credentials = credentials,
        inputsEnabled = false,
    )

    /**
     * The state of the screen when there is an error logging in.
     */
    data class SubmissionError(
        override val credentials: Credentials,
        val errorMessage: UIText,
    ) : LoginViewState(
        credentials = credentials,
    )

    object Completed : LoginViewState(
        credentials = Credentials(),
        inputsEnabled = false,
    )
}
