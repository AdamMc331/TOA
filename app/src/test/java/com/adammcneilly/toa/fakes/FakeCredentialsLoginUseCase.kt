package com.adammcneilly.toa.fakes

import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.LoginResult
import com.adammcneilly.toa.login.domain.usecase.CredentialsLoginUseCase
import io.mockk.coEvery
import io.mockk.mockk

class FakeCredentialsLoginUseCase {

    val mock: CredentialsLoginUseCase = mockk()

    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: LoginResult,
    ) {
        coEvery {
            mock(credentials)
        } returns result
    }
}
