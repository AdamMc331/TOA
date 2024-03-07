package com.adammcneilly.toa.login.domain.usecase

import com.adammcneilly.toa.fakes.FakeLoginRepository
import com.adammcneilly.toa.fakes.FakeTokenRepository
import com.adammcneilly.toa.login.domain.model.AuthToken
import com.adammcneilly.toa.login.domain.model.Credentials
import com.adammcneilly.toa.login.domain.model.Email
import com.adammcneilly.toa.login.domain.model.InvalidCredentialsException
import com.adammcneilly.toa.login.domain.model.LoginResponse
import com.adammcneilly.toa.login.domain.model.LoginResult
import com.adammcneilly.toa.login.domain.model.Password
import com.adammcneilly.toa.login.domain.model.RefreshToken
import com.adammcneilly.toa.login.domain.model.Token
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ProdCredentialsLoginUseCaseTest {
    private val defaultCredentials = Credentials(
        email = Email("testy@mctestface.com"),
        password = Password("Hunter2"),
    )

    private val defaultToken = Token(
        authToken = AuthToken("Auth"),
        refreshToken = RefreshToken("Refresh"),
    )

    private lateinit var loginRepository: FakeLoginRepository
    private lateinit var tokenRepository: FakeTokenRepository

    @Before
    fun setUp() {
        loginRepository = FakeLoginRepository()
        tokenRepository = FakeTokenRepository()
    }

    @Test
    fun testSuccessfulLogin() =
        runBlockingTest {
            val loginResponse = Result.success(
                LoginResponse(
                    token = defaultToken,
                ),
            )

            loginRepository.mockLoginWithCredentials(
                defaultCredentials,
                loginResponse,
            )

            val useCase = ProdCredentialsLoginUseCase(
                loginRepository = loginRepository.mock,
                tokenRepository = tokenRepository.mock,
            )

            val result = useCase.login(defaultCredentials)
            assertThat(result).isEqualTo(LoginResult.Success)
            tokenRepository.verifyTokenStored(defaultToken)
        }

    @Test
    fun testUnknownFailureLogin() =
        runBlockingTest {
            val loginResponse: Result<LoginResponse> = Result.failure(
                Throwable("Adam fucked up"),
            )

            loginRepository.mockLoginWithCredentials(
                defaultCredentials,
                loginResponse,
            )

            val useCase = ProdCredentialsLoginUseCase(
                loginRepository = loginRepository.mock,
                tokenRepository = tokenRepository.mock,
            )

            val result = useCase.login(defaultCredentials)
            assertThat(result).isEqualTo(LoginResult.Failure.Unknown)
            tokenRepository.verifyNoTokenStored()
        }

    @Test
    fun testInvalidCredentialLogin() =
        runBlockingTest {
            val loginResponse: Result<LoginResponse> = Result.failure(
                InvalidCredentialsException(),
            )

            loginRepository.mockLoginWithCredentials(
                defaultCredentials,
                loginResponse,
            )

            val useCase = ProdCredentialsLoginUseCase(
                loginRepository = loginRepository.mock,
                tokenRepository = tokenRepository.mock,
            )

            val result = useCase.login(defaultCredentials)
            assertThat(result).isEqualTo(LoginResult.Failure.InvalidCredentials)
            tokenRepository.verifyNoTokenStored()
        }

    @Test
    fun testEmptyCredentialLogin() =
        runBlockingTest {
            val emptyCredentials = Credentials()

            val useCase = ProdCredentialsLoginUseCase(
                loginRepository = loginRepository.mock,
                tokenRepository = tokenRepository.mock,
            )

            val result = useCase.login(emptyCredentials)
            assertThat(result).isEqualTo(
                LoginResult.Failure.EmptyCredentials(
                    emptyEmail = true,
                    emptyPassword = true,
                ),
            )

            loginRepository.verifyNoLoginCall()
            tokenRepository.verifyNoTokenStored()
        }
}
