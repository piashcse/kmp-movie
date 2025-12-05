package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.celebrities
import kmp_movie.composeapp.generated.resources.movies
import kmp_movie.composeapp.generated.resources.tv_series
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import navigation.AiringTodayTvSeries
import navigation.ArtistDetail
import navigation.MovieDetail
import navigation.NowPlayingMovie
import navigation.OnTheAirTvSeries
import navigation.PopularCelebrity
import navigation.PopularMovie
import navigation.PopularTvSeries
import navigation.Route
import navigation.Search
import navigation.TopLevelRoute
import navigation.TopRatedMovie
import navigation.TopRatedTvSeries
import navigation.TrendingCelebrity
import navigation.TvSeriesDetail
import navigation.UpcomingMovie
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.component.KMPNavigationSuiteScaffold
import ui.screens.AppViewModel
import ui.screens.celebrities.popular.PopularCelebrities
import ui.screens.celebrities.trending.TrendingCelebrities
import ui.screens.movie.now_playing.NowPlayingScreen
import ui.screens.artist_detail.ArtistDetail as ArtistDetailScreen
import ui.screens.movie.detail.MovieDetail as MovieDetailScreen
import ui.screens.movie.popular.PopularMovie as PopularMovieScreen
import ui.screens.movie.top_rated.TopRatedMovie as TopRatedMovieScreen
import ui.screens.movie.upcoming.UpcomingMovie as UpcomingMovieScreen
import ui.screens.tv_series.airing_today.AiringTodayTvSeries as AiringTodayTvSeriesScreen
import ui.screens.tv_series.detail.TvSeriesDetail as TvSeriesDetailScreen
import ui.screens.tv_series.on_the_air.OnTheAirTvSeries as OnTheAirTvSeriesScreen
import ui.screens.tv_series.popular.PopularTvSeries as PopularTvSeriesScreen
import ui.screens.tv_series.top_rated.TopRatedTvSeries as TopRatedTvSeriesScreen
import ui.screens.search.SearchScreen
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun App(
    appViewModel: AppViewModel = koinViewModel(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    // We use rememberSaveable with a custom saver or just mutableStateListOf for now.
    // Ideally use rememberSerializable if available, but for simplicity/compatibility:
    val backStack = remember { mutableStateListOf<Route>(NowPlayingMovie) }
    
    val currentTopLevelRoute = backStack.lastOrNull { it is TopLevelRoute } as? TopLevelRoute ?: NowPlayingMovie
    val currentItems = getItemsForPage(getPageForRoute(currentTopLevelRoute))

    KMPNavigationSuiteScaffold(
        navigationSuiteItems = {
            currentItems.forEach { item ->
                item(
                    selected = backStack.lastOrNull() == item,
                    onClick = { backStack.add(item) },
                    icon = { Icon(imageVector = item.icon!!, contentDescription = item.title) },
                    label = { Text(text = item.title) },
                )
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                if (currentTopLevelRoute == NowPlayingMovie || currentTopLevelRoute == AiringTodayTvSeries || currentTopLevelRoute == PopularCelebrity) {
                     // Show FAB only on main screens or always? User said "Make the search funcitoality by flaoting action button"
                     // I'll show it always for now, or maybe hide it on detail screens if backStack.last() is not TopLevelRoute
                     val currentRoute = backStack.lastOrNull()
                     if (currentRoute is TopLevelRoute) {
                         FloatingActionButton(
                             onClick = { backStack.add(Search) }
                         ) {
                             Icon(Icons.Filled.Search, contentDescription = "Search")
                         }
                     }
                }
            }
        ) { paddingValues ->
            NavDisplay(
                modifier = Modifier.padding(paddingValues),
                backStack = backStack,
                entryProvider = entryProvider {
                    entry<NowPlayingMovie> { 
                        MainScreen(NowPlayingMovie, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<PopularMovie> { 
                        MainScreen(PopularMovie, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<TopRatedMovie> { 
                        MainScreen(TopRatedMovie, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<UpcomingMovie> { 
                        MainScreen(UpcomingMovie, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<AiringTodayTvSeries> { 
                        MainScreen(AiringTodayTvSeries, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<OnTheAirTvSeries> { 
                        MainScreen(OnTheAirTvSeries, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<PopularTvSeries> { 
                        MainScreen(PopularTvSeries, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<TopRatedTvSeries> { 
                        MainScreen(TopRatedTvSeries, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<PopularCelebrity> { 
                        MainScreen(PopularCelebrity, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    entry<TrendingCelebrity> { 
                        MainScreen(TrendingCelebrity, backStack, appViewModel, windowAdaptiveInfo)
                    }
                    
                    entry<MovieDetail> { args ->
                        val route = args as MovieDetail
                        MovieDetailScreen(
                            movieId = route.id,
                            onBack = { backStack.removeLast() },
                            onNavigateToDetail = { id -> backStack.add(MovieDetail(id)) },
                            onNavigateToArtist = { id -> backStack.add(ArtistDetail(id)) }
                        )
                    }
                    entry<ArtistDetail> { args ->
                        val route = args as ArtistDetail
                        ArtistDetailScreen(
                            personId = route.id,
                            onBack = { backStack.removeLast() },
                            onNavigateToMovie = { id -> backStack.add(MovieDetail(id)) },
                            onNavigateToTvSeries = { id -> backStack.add(TvSeriesDetail(id)) }
                        )
                    }
                    entry<TvSeriesDetail> { args ->
                        val route = args as TvSeriesDetail
                        TvSeriesDetailScreen(
                            seriesId = route.id,
                            onBack = { backStack.removeLast() },
                            onNavigateToDetail = { id -> backStack.add(TvSeriesDetail(id)) },
                            onNavigateToArtist = { id -> backStack.add(ArtistDetail(id)) }
                        )
                    }
                    entry<Search> {
                        SearchScreen(
                            onBack = { backStack.removeLast() },
                            onNavigateToMovie = { id -> backStack.add(MovieDetail(id)) },
                            onNavigateToTvSeries = { id -> backStack.add(TvSeriesDetail(id)) },
                            onNavigateToArtist = { id -> backStack.add(ArtistDetail(id)) }
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreen(
    route: TopLevelRoute,
    backStack: MutableList<Route>,
    viewModel: AppViewModel,
    windowAdaptiveInfo: WindowAdaptiveInfo
) {
    val initialPage = getPageForRoute(route)
    val pagerState = rememberPagerState(initialPage = initialPage) { 3 }
    
    LaunchedEffect(route) {
        val page = getPageForRoute(route)
        if (pagerState.currentPage != page) {
            pagerState.animateScrollToPage(page)
        }
    }

    LaunchedEffect(pagerState.settledPage) {
        val page = pagerState.settledPage
        val currentRoutePage = getPageForRoute(route)
        if (page != currentRoutePage) {
            val newRoute = getDefaultRouteForPage(page)
            if (backStack.lastOrNull() != newRoute) {
                backStack.add(newRoute)
            }
        }
    }

    TabScreen(
        pagerState = pagerState,
        padding = PaddingValues(0.dp),
        currentRoute = route,
        onNavigate = { dest -> backStack.add(dest) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(
    pagerState: PagerState, 
    padding: PaddingValues,
    currentRoute: TopLevelRoute,
    onNavigate: (Route) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        stringResource(Res.string.movies),
        stringResource(Res.string.tv_series),
        stringResource(Res.string.celebrities)
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            title,
                            color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    })
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            val routeToRender = if (page == getPageForRoute(currentRoute)) currentRoute else getDefaultRouteForPage(page)
            
            when (routeToRender) {
                NowPlayingMovie -> NowPlayingScreen(onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) })
                PopularMovie -> PopularMovieScreen(onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) })
                TopRatedMovie -> TopRatedMovieScreen(onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) })
                UpcomingMovie -> UpcomingMovieScreen(onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) })
                
                AiringTodayTvSeries -> AiringTodayTvSeriesScreen(onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) })
                OnTheAirTvSeries -> OnTheAirTvSeriesScreen(onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) })
                PopularTvSeries -> PopularTvSeriesScreen(onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) })
                TopRatedTvSeries -> TopRatedTvSeriesScreen(onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) })
                
                PopularCelebrity -> PopularCelebrities(onNavigateToDetail = { id -> onNavigate(ArtistDetail(id)) })
                TrendingCelebrity -> TrendingCelebrities(onNavigateToDetail = { id -> onNavigate(ArtistDetail(id)) })
                else -> {}
            }
        }
    }
}

fun getPageForRoute(route: TopLevelRoute): Int {
    return when (route) {
        NowPlayingMovie, PopularMovie, TopRatedMovie, UpcomingMovie -> 0
        AiringTodayTvSeries, OnTheAirTvSeries, PopularTvSeries, TopRatedTvSeries -> 1
        PopularCelebrity, TrendingCelebrity -> 2
    }
}

fun getDefaultRouteForPage(page: Int): TopLevelRoute {
    return when (page) {
        0 -> NowPlayingMovie
        1 -> AiringTodayTvSeries
        2 -> PopularCelebrity
        else -> NowPlayingMovie
    }
}

fun getItemsForPage(page: Int): List<TopLevelRoute> {
    return when (page) {
        0 -> listOf(NowPlayingMovie, PopularMovie, TopRatedMovie, UpcomingMovie)
        1 -> listOf(AiringTodayTvSeries, OnTheAirTvSeries, PopularTvSeries, TopRatedTvSeries)
        else -> listOf(PopularCelebrity, TrendingCelebrity)
    }
}