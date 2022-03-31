package com.adammcneilly.toa.fakes

import androidx.navigation.NavOptionsBuilder
import com.google.common.truth.Truth.assertThat
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * This is a concrete implementation of a [DestinationsNavigator] that records all requests made
 * so that we can assert the expected navigation occurred.
 */
class FakeDestinationsNavigator : DestinationsNavigator {
    private val navigatedRoutes = mutableListOf<String>()

    override fun clearBackStack(route: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun navigate(
        route: String,
        onlyIfResumed: Boolean,
        builder: NavOptionsBuilder.() -> Unit,
    ) {
        navigatedRoutes.add(route)
    }

    override fun navigateUp(): Boolean {
        TODO("Not yet implemented")
    }

    override fun popBackStack(): Boolean {
        TODO("Not yet implemented")
    }

    override fun popBackStack(route: String, inclusive: Boolean, saveState: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    fun verifyNavigatedToRoute(expectedRoute: String) {
        assertThat(navigatedRoutes).contains(expectedRoute)
    }
}
