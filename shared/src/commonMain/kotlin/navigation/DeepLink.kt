package navigation

import androidx.compose.runtime.Composable

/**
 * Keeps the app route in sync with the platform URL where the platform supports it.
 * Only the web target currently produces real URLs; other platforms use a no-op.
 */
interface DeepLinkController {
    /** Route encoded in the URL at cold start, or null when there is none. */
    fun initialRoute(): Route?

    /** Called whenever the navigator changes so the URL can mirror the current route. */
    fun onRouteChanged(route: Route)

    /** Subscribes to URL changes (back/forward navigation) and returns an unsubscribe handle. */
    fun observe(onRoute: (Route) -> Unit): () -> Unit
}

@Composable
expect fun rememberDeepLinkController(): DeepLinkController

private val topLevelDeepLinks: Map<String, TopLevelRoute> = mapOf(
    "movies/now-playing" to NowPlayingMovie,
    "movies/popular" to PopularMovie,
    "movies/top-rated" to TopRatedMovie,
    "movies/upcoming" to UpcomingMovie,
    "tv/airing-today" to AiringTodayTvSeries,
    "tv/on-the-air" to OnTheAirTvSeries,
    "tv/popular" to PopularTvSeries,
    "tv/top-rated" to TopRatedTvSeries,
    "celebrities/popular" to PopularCelebrity,
    "celebrities/trending" to TrendingCelebrity,
    "favorites/movies" to FavoriteMovie,
    "favorites/tv" to FavoriteTvSeries,
    "favorites/celebrities" to FavoriteCelebrity,
)

/** URL fragment for a route, for example `#/movie/27205` or `#/movies/popular`. */
fun Route.toDeepLink(): String = when (this) {
    is MovieDetail -> "movie/$id"
    is TvSeriesDetail -> "series/$id"
    is ArtistDetail -> "person/$id"
    Search -> "search"
    is TopLevelRoute -> topLevelDeepLinks.entries.firstOrNull { it.value == this }?.key.orEmpty()
}

/** Parses a URL fragment (with or without the leading `#`) back into a route. */
fun routeFromDeepLink(value: String): Route? {
    val path = value.trim().removePrefix("#").trim('/')
    if (path.isEmpty()) return null
    if (path == "search") return Search

    topLevelDeepLinks[path]?.let { return it }

    val parts = path.split('/')
    if (parts.size != 2) return null
    val id = parts[1].toIntOrNull() ?: return null
    return when (parts[0]) {
        "movie" -> MovieDetail(id)
        "series" -> TvSeriesDetail(id)
        "person" -> ArtistDetail(id)
        else -> null
    }
}

/**
 * Applies an incoming deep link to the navigator without duplicating the current page.
 * Top-level links switch the current tab; detail links push on top of it.
 */
fun Navigator.openDeepLink(route: Route) {
    if (currentRoute == route) return
    when (route) {
        is TopLevelRoute -> navigateToTopLevel(route)
        else -> navigate(route)
    }
}
