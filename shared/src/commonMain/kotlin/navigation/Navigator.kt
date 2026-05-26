package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

class Navigator(
    val initialRoute: Route,
) {
    internal val backStack: MutableList<Route> = mutableStateListOf(initialRoute)

    val currentRoute: Route get() = backStack.last()

    val canGoBack: Boolean get() = backStack.size > 1

    val currentTopLevelRoute: TopLevelRoute
        get() {
            val last = backStack.lastOrNull()
            if (last is TopLevelRoute) return last
            return backStack.dropLast(1).lastOrNull { it is TopLevelRoute } as? TopLevelRoute
                ?: (backStack.firstOrNull() as? TopLevelRoute)
                ?: error("No TopLevelRoute in back stack")
        }

    val isDetailScreen: Boolean
        get() = backStack.lastOrNull() !is TopLevelRoute

    fun navigate(route: Route) {
        backStack.add(route)
    }

    fun replace(route: Route) {
        if (backStack.isNotEmpty()) {
            backStack.removeLast()
        }
        backStack.add(route)
    }

    fun navigateToTopLevel(route: TopLevelRoute) {
        val lastTopLevelIndex = backStack.indexOfLast { it is TopLevelRoute }
        if (lastTopLevelIndex >= 0) {
            while (backStack.size > lastTopLevelIndex) {
                backStack.removeLast()
            }
        }
        backStack.add(route)
    }

    fun popBackStack(): Boolean {
        return if (canGoBack) {
            backStack.removeLast()
            true
        } else false
    }
}

@Composable
fun rememberNavigator(initialRoute: Route): Navigator {
    return remember { Navigator(initialRoute) }
}
