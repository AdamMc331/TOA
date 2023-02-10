package com.adammcneilly.toa

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.adammcneilly.toa.login.domain.model.AuthToken
import com.adammcneilly.toa.login.domain.model.RefreshToken
import com.adammcneilly.toa.login.domain.model.Token
import com.adammcneilly.toa.login.domain.repository.TokenRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var tokenRepository: TokenRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun showLoginWhenNotLoggedIn() {
        runBlocking {
            // Clearing token simulates being logged out
            tokenRepository.clearToken()
        }

        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("LOG IN").assertIsDisplayed()
        composeTestRule.onNodeWithText("SIGN UP").assertIsDisplayed()
    }

    @Test
    fun showTaskListWhenLoggedIn() {
        runBlocking {
            // Storing a token simulates being logged in
            tokenRepository.storeToken(
                token = Token(
                    authToken = AuthToken("Test"),
                    refreshToken = RefreshToken("Test"),
                ),
            )
        }

        composeTestRule
            .onNodeWithText("You have nothing scheduled for this day. Make time for yourself")
            .assertIsDisplayed()
    }
}
