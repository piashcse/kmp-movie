package navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import ui.movie.artist_detail.ArtistDetail
import ui.movie.detail.MovieDetail
import ui.movie.now_playing.NowPlayingScreen
import ui.movie.popular.PopularMovie
import ui.movie.top_rated.TopRatedMovie
import ui.movie.upcoming.UpcomingMovie
import ui.tv_series.airing_today.AiringTodayTvSeries
import ui.tv_series.detail.TvSeriesDetail
import ui.tv_series.on_the_air.OnTheAirTvSeries
import ui.tv_series.popular.PopularTvSeries
import ui.tv_series.top_rated.TopRatedTvSeries

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Navigation(navigator: Navigator, page:Int) {
    NavHost(
        navigator = navigator,
        initialRoute = initialScreen(page),
    ) {
        scene(route = NavigationScreen.NowPlayingMovie.route) {
            NowPlayingScreen(navigator)
        }
        scene(route = NavigationScreen.PopularMovie.route) {
            PopularMovie(navigator)
        }
        scene(route = NavigationScreen.TopRatedMovie.route) {
            TopRatedMovie(navigator)
        }
        scene(route = NavigationScreen.UpcomingMovie.route) {
            UpcomingMovie(navigator)
        }
        scene(route = NavigationScreen.MovieDetail.route.plus(NavigationScreen.MovieDetail.objectPath)) { backStackEntry ->
            val id: Int? = backStackEntry.path<Int>(NavigationScreen.MovieDetail.objectName)
            id?.let {
                MovieDetail(navigator, it)
            }
        }
        scene(route = NavigationScreen.ArtistDetail.route.plus(NavigationScreen.ArtistDetail.objectPath)) { backStackEntry ->
            val id: Int? = backStackEntry.path<Int>(NavigationScreen.ArtistDetail.objectName)
            id?.let {
                ArtistDetail(it)
            }
        }
        scene(route = NavigationScreen.AiringTodayTvSeries.route) {
            AiringTodayTvSeries(navigator)
        }
        scene(route = NavigationScreen.OnTheAirTvSeries.route) {
            OnTheAirTvSeries(navigator)
        }
        scene(route = NavigationScreen.PopularTvSeries.route) {
            PopularTvSeries(navigator)
        }
        scene(route = NavigationScreen.TopRatedTvSeries.route) {
            TopRatedTvSeries(navigator)
        }
        scene(route = NavigationScreen.TvSeriesDetail.route.plus(NavigationScreen.TvSeriesDetail.objectPath)) { backStackEntry ->
            val id: Int? = backStackEntry.path<Int>(NavigationScreen.TvSeriesDetail.objectName)
            id?.let {
                TvSeriesDetail(navigator, it)
            }
        }
    }
}

@Composable
fun currentRoute(navigator: Navigator): String? {
    val routeName = navigator.currentEntry.collectAsState(null).value?.route?.route
    routeName?.let {
        return it.substringBefore("/")
    } ?: run {
        return ""
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun initialScreen(page:Int): String {
    return if (page == 0) {
        NavigationScreen.NowPlayingMovie.route
    } else {
        NavigationScreen.AiringTodayTvSeries.route
    }
}