package com.adammcneilly.toa.login.domain.model

import com.adammcneilly.toa.login.domain.usecase.Email
import com.adammcneilly.toa.login.domain.usecase.Password

sealed class LoginType {
    data class Credentials(
        val email: Email,
        val password: Password,
    ) : LoginType()

    object Google : LoginType()
}
