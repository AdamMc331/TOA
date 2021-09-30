package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.LoginResponse
import com.adammcneilly.toa.login.domain.repository.LoginRepository
import io.mockk.coEvery
import io.mockk.mockk

/**
 * A fake implementation of a [LoginRepository] that wraps all of our mock work.
 */
class FakeLoginRepository {
    val mock: LoginRepository = mockk()

    fun mockLoginWithCredentials(
        credentials: Credentials,
        result: Result<LoginResponse>,
    ) {
        coEvery {
            mock.login(credentials)
        } returns result
    }
}
