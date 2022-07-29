package com.adammcneilly.toa.session

/**
 * This defines the various states of the user's session in the app. Specifically
 * with regards to their authentication status.
 */
enum class SessionState {
    /**
     * This state is shown when the app first starts, and we have no information
     * about the user's authentication state.
     */
    UNINITIALIZED,

    /**
     * This state occurs when the user has been authenticated to use the app.
     */
    LOGGED_IN,

    /**
     * This state occurs when the user is not authenticated, such as using it
     * for the first time, or if their authentication information expired.
     */
    LOGGED_OUT,
}
