package com.adammcneilly.toa.login.domain.repository

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.login.domain.model.AuthToken
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.LoginResponse
import com.adammcneilly.toa.login.domain.model.RefreshToken
import com.adammcneilly.toa.login.domain.model.Token
import javax.inject.Inject

/**
 * This is a sample [LoginRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing sake.
 */
class DemoLoginRepository @Inject constructor() : LoginRepository {

    override suspend fun login(credentials: Credentials): Result<LoginResponse> {
        val defaultToken = Token(
            AuthToken(""),
            RefreshToken(""),
        )

        val defaultResponse = LoginResponse(defaultToken)

        return Result.Success(defaultResponse)
    }
}
