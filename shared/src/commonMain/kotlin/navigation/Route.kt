package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface Route

sealed interface TopLevelRoute : Route {
    val title: String
    val icon: ImageVector
}

data object NowPlayingMovie : TopLevelRoute {
    override val title = "Now playing"
    override val icon = Icons.Filled.PlayArrow
}

data object PopularMovie : TopLevelRoute {
    override val title = "Popular"
    override val icon = Icons.AutoMirrored.Filled.List
}

data object TopRatedMovie : TopLevelRoute {
    override val title = "Top rated"
    override val icon = Icons.Filled.Star
}

data object UpcomingMovie : TopLevelRoute {
    override val title = "Upcoming"
    override val icon = Icons.Filled.KeyboardArrowDown
}

data object AiringTodayTvSeries : TopLevelRoute {
    override val title = "Airing today"
    override val icon = Icons.Filled.PlayArrow
}

data object OnTheAirTvSeries : TopLevelRoute {
    override val title = "On the air"
    override val icon = Icons.AutoMirrored.Filled.List
}

data object PopularTvSeries : TopLevelRoute {
    override val title = "Popular"
    override val icon = Icons.Filled.Favorite
}

data object TopRatedTvSeries : TopLevelRoute {
    override val title = "Top rated"
    override val icon = Icons.Filled.Star
}

data object TrendingCelebrity : TopLevelRoute {
    override val title = "Trending"
    override val icon = Icons.AutoMirrored.Filled.List
}

data object PopularCelebrity : TopLevelRoute {
    override val title = "Popular"
    override val icon = Icons.Filled.Favorite
}

data class MovieDetail(val id: Int) : Route

data class ArtistDetail(val id: Int) : Route

data class TvSeriesDetail(val id: Int) : Route

data object Search : Route

data object FavoriteMovie : TopLevelRoute {
    override val title = "Movies"
    override val icon = Icons.Filled.PlayArrow
}

data object FavoriteTvSeries : TopLevelRoute {
    override val title = "TV Series"
    override val icon = Icons.AutoMirrored.Filled.List
}

data object FavoriteCelebrity : TopLevelRoute {
    override val title = "Celebrities"
    override val icon = Icons.Filled.Star
}
