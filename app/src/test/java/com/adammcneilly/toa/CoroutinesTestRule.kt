package com.adammcneilly.toa

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * This is a custom Junit Rule that will override the coroutine dispatchers in unit tests. We need
 * this because by default, coroutines launch on the thread they're called on, which is the main
 * thread in Android.
 *
 * In our unit tests, we don't have an Android main thread, so we make a call to [Dispatchers.setMain]
 * here.
 */
class CoroutinesTestRule(
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
