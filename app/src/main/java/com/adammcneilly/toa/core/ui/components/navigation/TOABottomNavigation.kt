package com.adammcneilly.toa.core.ui.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun TOABottomNavigation(
    navigationConfig: TOANavigationConfig,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        navigationConfig.tabs.forEach { tab ->
            NavigationBarItem(
                selected = tab == navigationConfig.selectedTab,
                onClick = {
                    navigationConfig.onTabClicked.invoke(tab)
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = stringResource(id = tab.labelTextRes),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = tab.labelTextRes),
                    )
                },
            )
        }
    }
}
