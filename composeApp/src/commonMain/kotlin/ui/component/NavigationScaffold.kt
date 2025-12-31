/*
 * Copyright 2024 NGApps Dev (https://github.com/ngapp-dev). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * KMP Movie navigation suite scaffold with item and content slots.
 * Wraps Material 3 [NavigationSuiteScaffold].
 *
 * @param modifier Modifier to be applied to the navigation suite scaffold.
 * @param navigationSuiteItems A slot to display multiple items via [KMPNavigationSuiteScope].
 * @param windowAdaptiveInfo The window adaptive info.
 * @param content The app content inside the scaffold.
 */
@Composable
fun KMPNavigationSuiteScaffold(
    navigationSuiteItems: KMPNavigationSuiteScope.() -> Unit = {},
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit,
) {
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = KMPNavigationDefaults.navigationBarSelectedItemColor(),
            unselectedIconColor = KMPNavigationDefaults.navigationBarUnselectedItemColor(),
            selectedTextColor = KMPNavigationDefaults.navigationBarSelectedItemColor(),
            unselectedTextColor = KMPNavigationDefaults.navigationBarUnselectedItemColor(),
            indicatorColor = Color.Transparent,
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = KMPNavigationDefaults.navigationRailSelectedItemColor(),
            unselectedIconColor = KMPNavigationDefaults.navigationRailUnselectedItemColor(),
            selectedTextColor = KMPNavigationDefaults.navigationRailSelectedItemColor(),
            unselectedTextColor = KMPNavigationDefaults.navigationRailUnselectedItemColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = KMPNavigationDefaults.navigationRailSelectedItemColor(),
            unselectedIconColor = KMPNavigationDefaults.navigationRailUnselectedItemColor(),
            selectedTextColor = KMPNavigationDefaults.navigationRailSelectedItemColor(),
            unselectedTextColor = KMPNavigationDefaults.navigationRailUnselectedItemColor(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            KMPNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = KMPNavigationDefaults.navigationContainerColor(),
            navigationRailContainerColor = KMPNavigationDefaults.navigationRailContainerColor(),
        ),
        modifier = modifier,
    ) {
        content()
    }
}

/**
 * A wrapper around [NavigationSuiteScope] to declare navigation items.
 */
class KMPNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

/**
 * KMP Movie navigation default values.
 */
object KMPNavigationDefaults {
    @Composable
    fun navigationContainerColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationRailContainerColor() = MaterialTheme.colorScheme.surface

    @Composable
    fun navigationBarSelectedItemColor() = MaterialTheme.colorScheme.onPrimary

    @Composable
    fun navigationBarUnselectedItemColor() = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)

    @Composable
    fun navigationRailSelectedItemColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun navigationRailUnselectedItemColor() = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

}