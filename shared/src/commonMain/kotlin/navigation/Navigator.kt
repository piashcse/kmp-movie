package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

class Navigator(
    initialRoute: Route,
    restoredBackStack: List<Route> = listOf(initialRoute),
) {
    internal val backStack: MutableList<Route> = mutableStateListOf<Route>().apply {
        val restored = restoredBackStack.ifEmpty { listOf(initialRoute) }
        // A deep link can start on a detail/search screen, but navigation always needs a
        // top-level destination underneath it for the tabs and back handling.
        if (restored.firstOrNull() !is TopLevelRoute) {
            add(NowPlayingMovie)
        }
        addAll(restored)
    }

    val currentRoute: Route get() = backStack.last()

    val canGoBack: Boolean get() = backStack.size > 1

    val currentTopLevelRoute: TopLevelRoute
        get() {
            for (index in backStack.indices.reversed()) {
                val route = backStack[index]
                if (route is TopLevelRoute) return route
            }
            error("No TopLevelRoute in back stack")
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

private val topLevelRoutesByKey: Map<String, TopLevelRoute> = listOf(
    NowPlayingMovie,
    PopularMovie,
    TopRatedMovie,
    UpcomingMovie,
    AiringTodayTvSeries,
    OnTheAirTvSeries,
    PopularTvSeries,
    TopRatedTvSeries,
    TrendingCelebrity,
    PopularCelebrity,
    FavoriteMovie,
    FavoriteTvSeries,
    FavoriteCelebrity,
).associateBy { it::class.simpleName.orEmpty() }

internal fun Route.saveKey(): String = when (this) {
    is MovieDetail -> "movie:$id"
    is TvSeriesDetail -> "tv:$id"
    is ArtistDetail -> "person:$id"
    Search -> "search"
    is TopLevelRoute -> "top:${this::class.simpleName}"
}

internal fun decodeRoute(key: String): Route? = when {
    key == "search" -> Search
    key.startsWith("movie:") -> key.removePrefix("movie:").toIntOrNull()?.let(::MovieDetail)
    key.startsWith("tv:") -> key.removePrefix("tv:").toIntOrNull()?.let(::TvSeriesDetail)
    key.startsWith("person:") -> key.removePrefix("person:").toIntOrNull()?.let(::ArtistDetail)
    key.startsWith("top:") -> topLevelRoutesByKey[key.removePrefix("top:")]
    else -> null
}

private val NavigatorSaver: Saver<Navigator, String> = Saver(
    save = { navigator -> navigator.backStack.joinToString("|") { it.saveKey() } },
    restore = { keys ->
        val stack = keys.split("|").mapNotNull(::decodeRoute)
        Navigator(NowPlayingMovie, stack.ifEmpty { listOf(NowPlayingMovie) })
    },
)

@Composable
fun rememberNavigator(initialRoute: Route): Navigator =
    rememberSaveable(saver = NavigatorSaver) { Navigator(initialRoute) }
