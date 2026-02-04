package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route

@Serializable
sealed interface TopLevelRoute : Route {
    val title: String
    // We can't serialize ImageVector directly easily if we want to persist state fully, 
    // but for now we can use a property that returns it, or just use an object that maps to it.
    // Since these are objects, it's fine.
    val icon: ImageVector?
}

@Serializable
data object NowPlayingMovie : TopLevelRoute {
    override val title = "Now playing"
    override val icon = Icons.Filled.Movie
}

@Serializable
data object PopularMovie : TopLevelRoute {
    override val title = "Popular"
    override val icon = Icons.Filled.Timeline
}

@Serializable
data object TopRatedMovie : TopLevelRoute {
    override val title = "Top rated"
    override val icon = Icons.Filled.Star
}

@Serializable
data object UpcomingMovie : TopLevelRoute {
    override val title = "Upcoming"
    override val icon = Icons.Filled.KeyboardArrowDown
}

@Serializable
data object AiringTodayTvSeries : TopLevelRoute {
    override val title = "Airing today"
    override val icon = Icons.Filled.Movie
}

@Serializable
data object OnTheAirTvSeries : TopLevelRoute {
    override val title = "On the air"
    override val icon = Icons.Filled.Timeline
}

@Serializable
data object PopularTvSeries : TopLevelRoute {
    override val title = "Popular"
    override val icon = Icons.Filled.Favorite
}

@Serializable
data object TopRatedTvSeries : TopLevelRoute {
    override val title = "Top rated"
    override val icon = Icons.Filled.Star
}

@Serializable
data object TrendingCelebrity : TopLevelRoute {
    override val title = "Trending"
    override val icon = Icons.Filled.Timeline
}

@Serializable
data object PopularCelebrity : TopLevelRoute {
    override val title = "Popular"
    override val icon = Icons.Filled.Favorite
}

@Serializable
data class MovieDetail(val id: Int) : Route

@Serializable
data class ArtistDetail(val id: Int) : Route

@Serializable
data class TvSeriesDetail(val id: Int) : Route

@Serializable
data object Search : Route

@Serializable
data object Genres : TopLevelRoute {
    override val title = "Genres"
    override val icon = Icons.Filled.Favorite // Using favorite icon temporarily
}

@Serializable
data class GenreContent(val genreId: Int, val genreName: String) : Route
