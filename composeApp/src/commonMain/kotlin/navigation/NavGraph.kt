package navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import ui.movie.artistdetail.ArtistDetail
import ui.movie.detail.MovieDetail
import ui.movie.now_playing.NowPlayingScreen
import ui.movie.popular.PopularMovie
import ui.movie.top_rated.TopRatedMovie
import ui.movie.upcoming.UpcomingMovie
import ui.tv_series.airing_today.AiringTodayTvSeries
import ui.tv_series.on_the_air.OnTheAirTvSeries
import ui.tv_series.popular.PopularTvSeries
import ui.tv_series.top_rated.TopRatedTvSeries

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Navigation(navigator: Navigator, pagerState: PagerState) {
    NavHost(
        navigator = navigator,
        initialRoute = initialScreen(pagerState),
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
    }
}

@Composable
fun currentRoute(navigator: Navigator): String? {
    return navigator.currentEntry.collectAsState(null).value?.route?.route

}
@OptIn(ExperimentalFoundationApi::class)
fun initialScreen(pagerState: PagerState): String {
    return if (pagerState.currentPage == 0){
        NavigationScreen.NowPlayingMovie.route
    }else{
        NavigationScreen.AiringTodayTvSeries.route
    }
}