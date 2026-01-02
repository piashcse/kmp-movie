package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import theme.KmpMovieTheme
import theme.ProvideThemeState
import theme.ThemeModeToggle
import theme.shouldUseDarkTheme
import ui.component.KMPNavigationSuiteScaffold
import ui.screens.AppViewModel
import ui.screens.celebrities.popular.PopularCelebritiesScreen
import ui.screens.celebrities.trending.TrendingCelebritiesScreen
import ui.screens.movie.now_playing.NowPlayingScreen
import ui.screens.artist_detail.ArtistDetail as ArtistDetailScreen
import ui.screens.movie.detail.MovieDetail as MovieDetailScreen
import ui.screens.movie.popular.PopularMovieScreen
import ui.screens.movie.top_rated.TopRatedMovieScreen
import ui.screens.movie.upcoming.UpcomingMovieScreen
import ui.screens.tv_series.airing_today.AiringTodayTvSeriesScreen
import ui.screens.tv_series.detail.TvSeriesDetail as TvSeriesDetailScreen
import ui.screens.tv_series.on_the_air.OnTheAirTvSeriesScreen
import ui.screens.tv_series.popular.PopularTvSeriesScreen
import ui.screens.tv_series.top_rated.TopRatedTvSeriesScreen
import ui.screens.search.SearchScreen

// Page constants for better code clarity
private const val PAGE_MOVIES = 0
private const val PAGE_TV_SERIES = 1
private const val PAGE_CELEBRITIES = 2
private const val PAGE_COUNT = 3

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun App(
    appViewModel: AppViewModel = koinViewModel(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    ProvideThemeState(appViewModel = appViewModel) {
        val darkTheme = shouldUseDarkTheme()

        KmpMovieTheme(
            darkTheme = darkTheme,
        ) {
            val backStack = remember { mutableStateListOf<Route>(NowPlayingMovie) }
            val currentRoute = backStack.lastOrNull() ?: NowPlayingMovie

            // Determine the current top-level route for navigation items
            val currentTopLevelRoute = when {
                currentRoute is TopLevelRoute -> currentRoute
                else -> backStack.dropLast(1).lastOrNull { it is TopLevelRoute } as? TopLevelRoute ?: NowPlayingMovie
            }

            val currentPage = getPageForRoute(currentTopLevelRoute)
            val currentItems = getItemsForPage(currentPage)
            val isDetailScreen = currentRoute !is TopLevelRoute

            // Content that will be rendered with or without navigation
            val content: @Composable () -> Unit = {
                Scaffold(
                    floatingActionButton = {
                        // Show FAB on all top-level routes (bottom navigation items) except detail screens
                        if (currentRoute is TopLevelRoute) {
                            FloatingActionButton(onClick = { backStack.add(Search) }) {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                            }
                        }
                    }
                ) { paddingValues ->
                    NavDisplay(
                        modifier = Modifier.padding(paddingValues),
                        backStack = backStack,
                        entryProvider = entryProvider {
                            // Movie routes
                            entry<NowPlayingMovie> { MainScreen(NowPlayingMovie, backStack) }
                            entry<PopularMovie> { MainScreen(PopularMovie, backStack) }
                            entry<TopRatedMovie> { MainScreen(TopRatedMovie, backStack) }
                            entry<UpcomingMovie> { MainScreen(UpcomingMovie, backStack) }

                            // TV Series routes
                            entry<AiringTodayTvSeries> { MainScreen(AiringTodayTvSeries, backStack) }
                            entry<OnTheAirTvSeries> { MainScreen(OnTheAirTvSeries, backStack) }
                            entry<PopularTvSeries> { MainScreen(PopularTvSeries, backStack) }
                            entry<TopRatedTvSeries> { MainScreen(TopRatedTvSeries, backStack) }

                            // Celebrity routes
                            entry<PopularCelebrity> { MainScreen(PopularCelebrity, backStack) }
                            entry<TrendingCelebrity> { MainScreen(TrendingCelebrity, backStack) }

                            // Detail screens
                            entry<MovieDetail> { args ->
                                MovieDetailScreen(
                                    movieId = args.id,
                                    onBack = { backStack.removeLast() },
                                    onNavigateToDetail = { id -> backStack.add(MovieDetail(id)) },
                                    onNavigateToArtist = { id -> backStack.add(ArtistDetail(id)) }
                                )
                            }
                            entry<ArtistDetail> { args ->
                                ArtistDetailScreen(
                                    personId = args.id,
                                    onBack = { backStack.removeLast() },
                                    onNavigateToMovie = { id -> backStack.add(MovieDetail(id)) },
                                    onNavigateToTvSeries = { id -> backStack.add(TvSeriesDetail(id)) }
                                )
                            }
                            entry<TvSeriesDetail> { args ->
                                TvSeriesDetailScreen(
                                    seriesId = args.id,
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

            // Conditionally wrap with navigation scaffold
            if (isDetailScreen) {
                content()
            } else {
                KMPNavigationSuiteScaffold(
                    navigationSuiteItems = {
                        currentItems.forEach { item ->
                            item(
                                selected = currentRoute == item,
                                onClick = { navigateTo(backStack, item) },
                                icon = { Icon(imageVector = item.icon!!, contentDescription = item.title) },
                                label = { Text(text = item.title) },
                            )
                        }
                    }
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun MainScreen(
    route: TopLevelRoute,
    backStack: MutableList<Route>
) {
    val initialPage = getPageForRoute(route)
    val pagerState = rememberPagerState(initialPage = initialPage) { PAGE_COUNT }
    
    LaunchedEffect(route) {
        val page = getPageForRoute(route)
        if (pagerState.currentPage != page) {
            pagerState.animateScrollToPage(page)
        }
    }

    LaunchedEffect(pagerState.settledPage) {
        val page = pagerState.settledPage
        if (page != getPageForRoute(route)) {
            val newRoute = getDefaultRouteForPage(page)
            if (backStack.lastOrNull() != newRoute) {
                navigateTo(backStack, newRoute)
            }
        }
    }

    TabScreen(
        pagerState = pagerState,
        currentRoute = route,
        onNavigate = { backStack.add(it) }
    )
}

/**
 * Smart navigation function that manages the backStack properly
 */
private fun navigateTo(backStack: MutableList<Route>, destination: Route) {
    val current = backStack.lastOrNull()
    when {
        destination is TopLevelRoute && current is TopLevelRoute -> {
            backStack.removeLast()
            backStack.add(destination)
        }
        else -> backStack.add(destination)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabScreen(
    pagerState: PagerState,
    currentRoute: TopLevelRoute,
    onNavigate: (Route) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        stringResource(Res.string.movies),
        stringResource(Res.string.tv_series),
        stringResource(Res.string.celebrities)
    )

    Column(Modifier.fillMaxSize()) {
        val currentTabTitle = tabs[pagerState.currentPage]
        TopAppBar(
            title = { Text(text = currentTabTitle) },
            actions = {
                ThemeModeToggle()
            }
        )

        PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            title,
                            color = if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                )
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            val route = if (page == getPageForRoute(currentRoute)) currentRoute
                       else getDefaultRouteForPage(page)

            when (route) {
                // Movies
                NowPlayingMovie -> NowPlayingScreen(onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) })
                PopularMovie -> PopularMovieScreen(onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) })
                TopRatedMovie -> TopRatedMovieScreen(onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) })
                UpcomingMovie -> UpcomingMovieScreen(onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) })

                // TV Series
                AiringTodayTvSeries -> AiringTodayTvSeriesScreen(onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) })
                OnTheAirTvSeries -> OnTheAirTvSeriesScreen(onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) })
                PopularTvSeries -> PopularTvSeriesScreen(onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) })
                TopRatedTvSeries -> TopRatedTvSeriesScreen(onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) })

                // Celebrities
                PopularCelebrity -> PopularCelebritiesScreen(onNavigateToDetail = { id -> onNavigate(ArtistDetail(id)) })
                TrendingCelebrity -> TrendingCelebritiesScreen(onNavigateToDetail = { id -> onNavigate(ArtistDetail(id)) })
            }
        }
    }
}

private fun getPageForRoute(route: TopLevelRoute): Int = when (route) {
    NowPlayingMovie, PopularMovie, TopRatedMovie, UpcomingMovie -> PAGE_MOVIES
    AiringTodayTvSeries, OnTheAirTvSeries, PopularTvSeries, TopRatedTvSeries -> PAGE_TV_SERIES
    PopularCelebrity, TrendingCelebrity -> PAGE_CELEBRITIES
}

private fun getDefaultRouteForPage(page: Int): TopLevelRoute = when (page) {
    PAGE_MOVIES -> NowPlayingMovie
    PAGE_TV_SERIES -> AiringTodayTvSeries
    PAGE_CELEBRITIES -> PopularCelebrity
    else -> NowPlayingMovie
}

private fun getItemsForPage(page: Int): List<TopLevelRoute> = when (page) {
    PAGE_MOVIES -> listOf(NowPlayingMovie, PopularMovie, TopRatedMovie, UpcomingMovie)
    PAGE_TV_SERIES -> listOf(AiringTodayTvSeries, OnTheAirTvSeries, PopularTvSeries, TopRatedTvSeries)
    PAGE_CELEBRITIES -> listOf(PopularCelebrity, TrendingCelebrity)
    else -> emptyList()
}