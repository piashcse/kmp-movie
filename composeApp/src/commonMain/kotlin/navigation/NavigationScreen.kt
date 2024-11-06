package navigation

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class NavigationScreen(
    val route: String,
    val title: String = "",
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.LiveTv, contentDescription = "tv"
        )
    },
    val objectName: String = "",
    val objectPath: String = ""
) {
    data object NowPlayingMovie : NavigationScreen("now_playing_movie")
    data object PopularMovie : NavigationScreen("popular_movie")
    data object TopRatedMovie : NavigationScreen("top_rated_movie")
    data object UpcomingMovie : NavigationScreen("upcoming_movie")
    data object AiringTodayTvSeries : NavigationScreen("airing_today_tv_series")
    data object OnTheAirTvSeries : NavigationScreen("on_the_air_tv_series")
    data object PopularTvSeries : NavigationScreen("popular_tv_series")
    data object TopRatedTvSeries : NavigationScreen("top_rated_tv_series")
    data object MovieDetail :
        NavigationScreen("movie_detail",  objectName = "id", objectPath = "/{id}")
    data object ArtistDetail :
        NavigationScreen("artist_detail", objectName = "id", objectPath = "/{id}")

    data object TvSeriesDetail :
        NavigationScreen("tv_series_detail",  objectName = "id", objectPath = "/{id}")

    data object NowPlayingMovieNav : NavigationScreen("now_playing_movie", title = "Now playing", navIcon = {
        Icon(
            Icons.Filled.Movie,
            contentDescription = "Movie",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object PopularMovieNav : NavigationScreen("popular_movie", title = "Popular", navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "Timeline",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object TopRatedMovieNav : NavigationScreen("top_rated_movie", title = "Top rated", navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "Star",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object UpcomingMovieNav : NavigationScreen("upcoming_movie", title = "Upcoming", navIcon = {
        Icon(
            Icons.Filled.KeyboardArrowDown,
            contentDescription = "KeyboardArrowDown",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object AiringTodayTvSeriesNav : NavigationScreen("airing_today_tv_series", title = "Airing today", navIcon = {
        Icon(
            Icons.Filled.Movie,
            contentDescription = "Movie",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object OnTheAirTvSeriesNav : NavigationScreen("on_the_air_tv_series", title = "On the air", navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "Timeline",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object PopularTvSeriesNav : NavigationScreen("popular_tv_series", title = "Popular", navIcon = {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Star",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object TopRatedTvSeriesNav : NavigationScreen("top_rated_tv_series", title = "Top rated", navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "Star",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
}