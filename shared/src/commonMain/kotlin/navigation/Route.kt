package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.airing_today
import kmp_movie.composeapp.generated.resources.celebrities
import kmp_movie.composeapp.generated.resources.favorites
import kmp_movie.composeapp.generated.resources.movies
import kmp_movie.composeapp.generated.resources.now_playing
import kmp_movie.composeapp.generated.resources.on_the_air
import kmp_movie.composeapp.generated.resources.popular
import kmp_movie.composeapp.generated.resources.top_rated
import kmp_movie.composeapp.generated.resources.trending
import kmp_movie.composeapp.generated.resources.tv_series
import kmp_movie.composeapp.generated.resources.upcoming
import org.jetbrains.compose.resources.StringResource

sealed interface Route

sealed interface TopLevelRoute : Route {
    val titleRes: StringResource
    val icon: ImageVector
}

data object NowPlayingMovie : TopLevelRoute {
    override val titleRes = Res.string.now_playing
    override val icon = Icons.Filled.PlayArrow
}

data object PopularMovie : TopLevelRoute {
    override val titleRes = Res.string.popular
    override val icon = Icons.AutoMirrored.Filled.List
}

data object TopRatedMovie : TopLevelRoute {
    override val titleRes = Res.string.top_rated
    override val icon = Icons.Filled.Star
}

data object UpcomingMovie : TopLevelRoute {
    override val titleRes = Res.string.upcoming
    override val icon = Icons.Filled.KeyboardArrowDown
}

data object AiringTodayTvSeries : TopLevelRoute {
    override val titleRes = Res.string.airing_today
    override val icon = Icons.Filled.PlayArrow
}

data object OnTheAirTvSeries : TopLevelRoute {
    override val titleRes = Res.string.on_the_air
    override val icon = Icons.AutoMirrored.Filled.List
}

data object PopularTvSeries : TopLevelRoute {
    override val titleRes = Res.string.popular
    override val icon = Icons.Filled.Favorite
}

data object TopRatedTvSeries : TopLevelRoute {
    override val titleRes = Res.string.top_rated
    override val icon = Icons.Filled.Star
}

data object TrendingCelebrity : TopLevelRoute {
    override val titleRes = Res.string.trending
    override val icon = Icons.AutoMirrored.Filled.List
}

data object PopularCelebrity : TopLevelRoute {
    override val titleRes = Res.string.popular
    override val icon = Icons.Filled.Favorite
}

data class MovieDetail(val id: Int) : Route

data class ArtistDetail(val id: Int) : Route

data class TvSeriesDetail(val id: Int) : Route

data object Search : Route

data object FavoriteMovie : TopLevelRoute {
    override val titleRes = Res.string.movies
    override val icon = Icons.Filled.PlayArrow
}

data object FavoriteTvSeries : TopLevelRoute {
    override val titleRes = Res.string.tv_series
    override val icon = Icons.AutoMirrored.Filled.List
}

data object FavoriteCelebrity : TopLevelRoute {
    override val titleRes = Res.string.celebrities
    override val icon = Icons.Filled.Star
}
