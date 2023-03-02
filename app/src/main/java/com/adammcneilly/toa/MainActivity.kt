@file:OptIn(ExperimentalMaterialNavigationApi::class)

package com.adammcneilly.toa

import android.R
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.adammcneilly.toa.core.ui.WindowSize
import com.adammcneilly.toa.core.ui.components.navigation.NavigationTab
import com.adammcneilly.toa.core.ui.components.navigation.NavigationType
import com.adammcneilly.toa.core.ui.components.navigation.TOABottomNavigation
import com.adammcneilly.toa.core.ui.components.navigation.TOANavigationDrawerContent
import com.adammcneilly.toa.core.ui.components.navigation.TOANavigationRail
import com.adammcneilly.toa.core.ui.rememberWindowSizeClass
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.destinations.LoginScreenDestination
import com.adammcneilly.toa.destinations.TaskListScreenDestination
import com.adammcneilly.toa.session.SessionState
import com.adammcneilly.toa.session.SessionViewModel
import com.adammcneilly.toa.tasklist.ui.TaskListScreen
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.spec.NavHostEngine
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val sessionViewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        keepSplashScreenVisibleWhileInitializing()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val windowSize = rememberWindowSizeClass()

            TOATheme {
                ConfigureSystemBars()

                ProvideWindowInsets {
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        val sessionState = sessionViewModel.sessionState.collectAsState()

                        val startRoute: Route? = when (sessionState.value) {
                            SessionState.UNINITIALIZED -> null
                            SessionState.LOGGED_IN -> TaskListScreenDestination
                            SessionState.LOGGED_OUT -> LoginScreenDestination
                        }

                        if (startRoute != null) {
                            TOANavHost(startRoute, windowSize)
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    private fun TOANavHost(
        startRoute: Route,
        windowSize: WindowSize,
    ) {
        val navigationEngine = rememberAnimatedNavHostEngine(
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                enterTransition = {
                    slideInHorizontally()
                },
                exitTransition = {
                    fadeOut()
                },
            ),
        )

        val navController = navigationEngine.rememberNavController()

        val windowSizeClass = calculateWindowSizeClass(activity = this)

        val navigationType = getNavigationType(windowSizeClass.widthSizeClass)

        val navigationTabs = listOf(
            NavigationTab.Home,
            NavigationTab.Settings,
        )

        when (navigationType) {
            NavigationType.BOTTOM_NAVIGATION -> {
                BottomNavigationContent(
                    startRoute,
                    navigationEngine,
                    navController,
                    windowSize,
                    navigationTabs
                )
            }

            NavigationType.NAVIGATION_RAIL -> {
                NavigationRailContent(
                    navController,
                    navigationTabs,
                    startRoute,
                    navigationEngine,
                    windowSize
                )
            }

            NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                PermanentNavigationDrawerContent(
                    navController,
                    navigationTabs,
                    startRoute,
                    navigationEngine,
                    windowSize
                )
            }
        }
    }

    @Composable
    private fun PermanentNavigationDrawerContent(
        navController: NavHostController,
        navigationTabs: List<NavigationTab>,
        startRoute: Route,
        navigationEngine: NavHostEngine,
        windowSize: WindowSize
    ) {
        PermanentNavigationDrawer(
            drawerContent = {
                TOANavigationDrawerContent(
                    navHostController = navController,
                    tabs = navigationTabs,
                )
            },
        ) {
            DestinationsNavHost(
                startRoute = startRoute,
                navGraph = NavGraphs.root,
                engine = navigationEngine,
                navController = navController,
                manualComposableCallsBuilder = {
                    composable(TaskListScreenDestination) {
                        TaskListScreen(
                            navigator = destinationsNavigator,
                            windowSize = windowSize,
                        )
                    }
                },
            )
        }
    }

    @Composable
    private fun NavigationRailContent(
        navController: NavHostController,
        navigationTabs: List<NavigationTab>,
        startRoute: Route,
        navigationEngine: NavHostEngine,
        windowSize: WindowSize
    ) {
        Row {
            TOANavigationRail(
                navHostController = navController,
                tabs = navigationTabs,
                modifier = Modifier
                    .width(80.dp),
            )

            DestinationsNavHost(
                startRoute = startRoute,
                navGraph = NavGraphs.root,
                engine = navigationEngine,
                navController = navController,
                manualComposableCallsBuilder = {
                    composable(TaskListScreenDestination) {
                        TaskListScreen(
                            navigator = destinationsNavigator,
                            windowSize = windowSize,
                        )
                    }
                },
                modifier = Modifier
                    .weight(1F),
            )
        }
    }

    @Composable
    private fun BottomNavigationContent(
        startRoute: Route,
        navigationEngine: NavHostEngine,
        navController: NavHostController,
        windowSize: WindowSize,
        tabs: List<NavigationTab>,
    ) {
        Column {
            DestinationsNavHost(
                startRoute = startRoute,
                navGraph = NavGraphs.root,
                engine = navigationEngine,
                navController = navController,
                manualComposableCallsBuilder = {
                    composable(TaskListScreenDestination) {
                        TaskListScreen(
                            navigator = destinationsNavigator,
                            windowSize = windowSize,
                        )
                    }
                },
                modifier = Modifier
                    .weight(1F),
            )

            TOABottomNavigation(
                navHostController = navController,
                tabs = tabs,
            )
        }
    }

    private fun keepSplashScreenVisibleWhileInitializing() {
        val content: View = findViewById(R.id.content)

        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val currentSessionState = sessionViewModel.sessionState.value
                    val isInitializing = currentSessionState == SessionState.UNINITIALIZED

                    return if (isInitializing) {
                        // We don't have data yet, don't draw anything.
                        false
                    } else {
                        // we're ready, start drawing
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }
                }
            }
        )
    }

    @Composable
    private fun ConfigureSystemBars() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
        }
    }

    private fun getNavigationType(
        widthSizeClass: WindowWidthSizeClass,
    ): NavigationType {
        return when (widthSizeClass) {
            WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAVIGATION_DRAWER
            WindowWidthSizeClass.Medium -> NavigationType.NAVIGATION_RAIL
            else -> NavigationType.BOTTOM_NAVIGATION
        }
    }
}
