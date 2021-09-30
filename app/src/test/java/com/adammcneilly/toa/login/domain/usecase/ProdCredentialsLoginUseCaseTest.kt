package com.adammcneilly.toa.login.domain.usecase

import com.adammcneilly.toa.core.data.Result
import com.adammcneilly.toa.fakes.FakeLoginRepository
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.Email
import com.adammcneilly.toa.login.domain.model.InvalidCredentialsException
import com.adammcneilly.toa.login.domain.model.LoginResponse
import com.adammcneilly.toa.login.domain.model.LoginResult
import com.adammcneilly.toa.login.domain.model.Password
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ProdCredentialsLoginUseCaseTest {

    @Test
    fun testSuccessfulLogin() = runBlockingTest {
        val inputCredentials = Credentials(
            email = Email("testy@mctestface.com"),
            password = Password("Hunter2"),
        )

        val loginResponse = Result.Success(
            LoginResponse(
                authToken = "Success",
            )
        )

        val loginRepository = FakeLoginRepository().apply {
            mockLoginWithCredentials(
                inputCredentials,
                loginResponse,
            )
        }

        val useCase = ProdCredentialsLoginUseCase(loginRepository.mock)
        val result = useCase(inputCredentials)
        assertThat(result).isEqualTo(LoginResult.Success)
    }

    @Test
    fun testUnknownFailureLogin() = runBlockingTest {
        val inputCredentials = Credentials(
            email = Email("testy@mctestface.com"),
            password = Password("Hunter2"),
        )

        val loginResponse: Result<LoginResponse> = Result.Error(
            Throwable("Adam fucked up")
        )

        val loginRepository = FakeLoginRepository().apply {
            mockLoginWithCredentials(
                inputCredentials,
                loginResponse,
            )
        }

        val useCase = ProdCredentialsLoginUseCase(loginRepository.mock)
        val result = useCase(inputCredentials)
        assertThat(result).isEqualTo(LoginResult.Failure.Unknown)
    }

    @Test
    fun testInvalidCredentialLogin() = runBlockingTest {
        val inputCredentials = Credentials(
            email = Email("testy@mctestface.com"),
            password = Password("Hunter2"),
        )

        val loginResponse: Result<LoginResponse> = Result.Error(
            InvalidCredentialsException()
        )

        val loginRepository = FakeLoginRepository().apply {
            mockLoginWithCredentials(
                inputCredentials,
                loginResponse,
            )
        }

        val useCase = ProdCredentialsLoginUseCase(loginRepository.mock)
        val result = useCase(inputCredentials)
        assertThat(result).isEqualTo(LoginResult.Failure.InvalidCredentials)
    }
}
