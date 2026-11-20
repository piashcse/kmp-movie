package ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationEventHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import data.model.local.MediaType
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.action_search
import kmp_movie.composeapp.generated.resources.celebrities
import kmp_movie.composeapp.generated.resources.favorites
import kmp_movie.composeapp.generated.resources.movies
import kmp_movie.composeapp.generated.resources.tv_series
import kotlinx.coroutines.launch
import navigation.AiringTodayTvSeries
import navigation.ArtistDetail
import navigation.FavoriteCelebrity
import navigation.FavoriteMovie
import navigation.FavoriteTvSeries
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
import navigation.rememberNavigator
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
import ui.screens.favorites.FavoritesScreen
import ui.screens.movie.now_playing.NowPlayingScreen
import ui.screens.movie.popular.PopularMovieScreen
import ui.screens.movie.top_rated.TopRatedMovieScreen
import ui.screens.movie.upcoming.UpcomingMovieScreen
import ui.screens.search.SearchScreen
import ui.screens.tv_series.airing_today.AiringTodayTvSeriesScreen
import ui.screens.tv_series.on_the_air.OnTheAirTvSeriesScreen
import ui.screens.tv_series.popular.PopularTvSeriesScreen
import ui.screens.tv_series.top_rated.TopRatedTvSeriesScreen
import ui.screens.artist_detail.ArtistDetail as ArtistDetailScreen
import ui.screens.movie.detail.MovieDetail as MovieDetailScreen
import ui.screens.tv_series.detail.TvSeriesDetail as TvSeriesDetailScreen

private const val PAGE_MOVIES = 0
private const val PAGE_TV_SERIES = 1
private const val PAGE_CELEBRITIES = 2
private const val PAGE_FAVORITES = 3
private const val PAGE_COUNT = 4
private const val TOP_LEVEL_STATE_KEY = "top_level"

private val pageTabs = listOf(
    PageTab(Res.string.movies),
    PageTab(Res.string.tv_series),
    PageTab(Res.string.celebrities),
    PageTab(Res.string.favorites),
)

private data class PageTab(
    val titleRes: org.jetbrains.compose.resources.StringResource,
)

@Composable
fun App(
    appViewModel: AppViewModel = koinViewModel(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfoV2(),
) {
    ProvideThemeState(appViewModel = appViewModel) {
        KmpMovieTheme(darkTheme = shouldUseDarkTheme()) {
            val navigator = rememberNavigator(NowPlayingMovie)
            val isDetailScreen = navigator.isDetailScreen
            // Keeps list/pager state (scroll position, selected tab) while a detail screen is open.
            val saveableStateHolder = rememberSaveableStateHolder()

            val backState = rememberNavigationEventState(NavigationEventInfo.None)
            NavigationEventHandler(
                state = backState,
                isBackEnabled = navigator.canGoBack,
                onBackCompleted = { navigator.popBackStack() },
            )

            if (isDetailScreen) {
                Crossfade(
                    targetState = navigator.currentRoute,
                    animationSpec = tween(300),
                    label = "detail_transition",
                ) { currentRoute ->
                    saveableStateHolder.SaveableStateProvider(currentRoute.toString()) {
                        Scaffold { paddingValues ->
                            DetailScreen(
                                navigator = navigator,
                                currentRoute = currentRoute,
                                modifier = Modifier.padding(paddingValues),
                            )
                        }
                    }
                }
            } else {
                val currentTopLevelRoute = navigator.currentTopLevelRoute
                val currentPage = getPageForRoute(currentTopLevelRoute)
                val currentItems = getItemsForPage(currentPage)

                KMPNavigationSuiteScaffold(
                    windowAdaptiveInfo = windowAdaptiveInfo,
                    navigationSuiteItems = {
                        currentItems.forEach { item ->
                            item(
                                selected = navigator.currentRoute == item,
                                onClick = { navigator.navigateToTopLevel(item) },
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = stringResource(item.titleRes),
                                    )
                                },
                                label = { Text(text = stringResource(item.titleRes)) },
                            )
                        }
                    }
                ) {
                    // One stable pager for all top-level routes. Previously this was wrapped in a
                    // Crossfade keyed by the route, so every tab/sub-tab switch recreated the
                    // pager and re-entered every list screen.
                    saveableStateHolder.SaveableStateProvider(TOP_LEVEL_STATE_KEY) {
                        ListScaffold(
                            navigator = navigator,
                            activeRoute = currentTopLevelRoute,
                            currentPage = currentPage,
                            saveableStateHolder = saveableStateHolder,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailScreen(
    navigator: navigation.Navigator,
    currentRoute: Route,
    modifier: Modifier = Modifier,
) {
    when (currentRoute) {
        is MovieDetail -> MovieDetailScreen(
            movieId = currentRoute.id,
            onBack = { navigator.popBackStack() },
            onNavigateToDetail = { id -> navigator.navigate(MovieDetail(id)) },
            onNavigateToArtist = { id -> navigator.navigate(ArtistDetail(id)) },
            modifier = modifier,
        )
        is ArtistDetail -> ArtistDetailScreen(
            personId = currentRoute.id,
            onBack = { navigator.popBackStack() },
            onNavigateToMovie = { id -> navigator.navigate(MovieDetail(id)) },
            onNavigateToTvSeries = { id -> navigator.navigate(TvSeriesDetail(id)) },
            modifier = modifier,
        )
        is TvSeriesDetail -> TvSeriesDetailScreen(
            seriesId = currentRoute.id,
            onBack = { navigator.popBackStack() },
            onNavigateToDetail = { id -> navigator.navigate(TvSeriesDetail(id)) },
            onNavigateToArtist = { id -> navigator.navigate(ArtistDetail(id)) },
            modifier = modifier,
        )
        is Search -> SearchScreen(
            onBack = { navigator.popBackStack() },
            onNavigateToMovie = { id -> navigator.navigate(MovieDetail(id)) },
            onNavigateToTvSeries = { id -> navigator.navigate(TvSeriesDetail(id)) },
            onNavigateToArtist = { id -> navigator.navigate(ArtistDetail(id)) },
            modifier = modifier,
        )
        else -> {}
    }
}

@Composable
private fun ListScaffold(
    navigator: navigation.Navigator,
    activeRoute: TopLevelRoute,
    currentPage: Int,
    saveableStateHolder: SaveableStateHolder,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(Search) }) {
                Icon(Icons.Filled.Search, contentDescription = stringResource(Res.string.action_search))
            }
        }
    ) { paddingValues ->
        MainScreen(
            navigator = navigator,
            activeRoute = activeRoute,
            currentPage = currentPage,
            saveableStateHolder = saveableStateHolder,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun MainScreen(
    navigator: navigation.Navigator,
    activeRoute: TopLevelRoute,
    currentPage: Int,
    saveableStateHolder: SaveableStateHolder,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = currentPage) { PAGE_COUNT }

    // Remember the selected sub-tab of each top-level page so switching pages (or opening a
    // detail and coming back) doesn't reset the bottom navigation to the first item.
    var moviesIndex by rememberSaveable { mutableStateOf(0) }
    var tvIndex by rememberSaveable { mutableStateOf(0) }
    var celebritiesIndex by rememberSaveable { mutableStateOf(0) }
    var favoritesIndex by rememberSaveable { mutableStateOf(0) }

    fun selectedIndex(page: Int): Int = when (page) {
        PAGE_MOVIES -> moviesIndex
        PAGE_TV_SERIES -> tvIndex
        PAGE_CELEBRITIES -> celebritiesIndex
        else -> favoritesIndex
    }

    fun routeForPage(page: Int): TopLevelRoute =
        getItemsForPage(page).getOrElse(selectedIndex(page)) { getDefaultRouteForPage(page) }

    fun selectIndex(page: Int, index: Int) {
        when (page) {
            PAGE_MOVIES -> moviesIndex = index
            PAGE_TV_SERIES -> tvIndex = index
            PAGE_CELEBRITIES -> celebritiesIndex = index
            else -> favoritesIndex = index
        }
    }

    LaunchedEffect(activeRoute) {
        val page = getPageForRoute(activeRoute)
        val index = getItemsForPage(page).indexOf(activeRoute)
        if (index >= 0) {
            selectIndex(page, index)
        }
        if (pagerState.currentPage != page) {
            pagerState.animateScrollToPage(page)
        }
    }

    LaunchedEffect(pagerState.settledPage) {
        val page = pagerState.settledPage
        val targetRoute = routeForPage(page)
        if (navigator.currentRoute != targetRoute) {
            navigator.navigateToTopLevel(targetRoute)
        }
    }

    TabScreen(
        pagerState = pagerState,
        routeForPage = { page -> routeForPage(page) },
        saveableStateHolder = saveableStateHolder,
        onNavigate = { navigator.navigate(it) },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabScreen(
    pagerState: PagerState,
    routeForPage: (Int) -> TopLevelRoute,
    saveableStateHolder: SaveableStateHolder,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            val currentTabIndex = pagerState.currentPage
            TopAppBar(
                title = { Text(text = stringResource(pageTabs[currentTabIndex].titleRes)) },
                actions = { ThemeModeToggle() },
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
                pageTabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        text = {
                            Text(
                                stringResource(tab.titleRes),
                                color = if (pagerState.currentPage == index) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            )
                        },
                    )
                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                val route = routeForPage(page)

                // Only the sub-route content animates; the pager, tab row and bottom bar stay
                // composed so switching tabs never tears the navigation shell down.
                Crossfade(
                    targetState = route,
                    animationSpec = tween(180),
                    label = "page_route_transition",
                ) { targetRoute ->
                    saveableStateHolder.SaveableStateProvider(targetRoute.toString()) {
                        TopLevelContent(route = targetRoute, onNavigate = onNavigate)
                    }
                }
            }
        }
    }
}

@Composable
private fun TopLevelContent(
    route: TopLevelRoute,
    onNavigate: (Route) -> Unit,
) {
    when (route) {
        is NowPlayingMovie -> NowPlayingScreen(
            onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) },
        )
        is PopularMovie -> PopularMovieScreen(
            onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) },
        )
        is TopRatedMovie -> TopRatedMovieScreen(
            onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) },
        )
        is UpcomingMovie -> UpcomingMovieScreen(
            onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) },
        )
        is AiringTodayTvSeries -> AiringTodayTvSeriesScreen(
            onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) },
        )
        is OnTheAirTvSeries -> OnTheAirTvSeriesScreen(
            onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) },
        )
        is PopularTvSeries -> PopularTvSeriesScreen(
            onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) },
        )
        is TopRatedTvSeries -> TopRatedTvSeriesScreen(
            onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) },
        )
        is PopularCelebrity -> PopularCelebritiesScreen(
            onNavigateToDetail = { id -> onNavigate(ArtistDetail(id)) },
        )
        is TrendingCelebrity -> TrendingCelebritiesScreen(
            onNavigateToDetail = { id -> onNavigate(ArtistDetail(id)) },
        )
        is FavoriteMovie -> FavoritesScreen(
            mediaType = MediaType.MOVIE,
            onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) },
        )
        is FavoriteTvSeries -> FavoritesScreen(
            mediaType = MediaType.TV,
            onNavigateToDetail = { id -> onNavigate(TvSeriesDetail(id)) },
        )
        is FavoriteCelebrity -> FavoritesScreen(
            mediaType = MediaType.PERSON,
            onNavigateToDetail = { id -> onNavigate(ArtistDetail(id)) },
        )
    }
}

private fun getPageForRoute(route: TopLevelRoute): Int = when (route) {
    is NowPlayingMovie, is PopularMovie, is TopRatedMovie, is UpcomingMovie -> PAGE_MOVIES
    is AiringTodayTvSeries, is OnTheAirTvSeries, is PopularTvSeries, is TopRatedTvSeries -> PAGE_TV_SERIES
    is PopularCelebrity, is TrendingCelebrity -> PAGE_CELEBRITIES
    is FavoriteMovie, is FavoriteTvSeries, is FavoriteCelebrity -> PAGE_FAVORITES
}

private fun getDefaultRouteForPage(page: Int): TopLevelRoute = when (page) {
    PAGE_MOVIES -> NowPlayingMovie
    PAGE_TV_SERIES -> AiringTodayTvSeries
    PAGE_CELEBRITIES -> PopularCelebrity
    PAGE_FAVORITES -> FavoriteMovie
    else -> NowPlayingMovie
}

private val movieRoutes = listOf(NowPlayingMovie, PopularMovie, TopRatedMovie, UpcomingMovie)
private val tvSeriesRoutes = listOf(AiringTodayTvSeries, OnTheAirTvSeries, PopularTvSeries, TopRatedTvSeries)
private val celebrityRoutes = listOf(PopularCelebrity, TrendingCelebrity)
private val favoriteRoutes = listOf(FavoriteMovie, FavoriteTvSeries, FavoriteCelebrity)

private fun getItemsForPage(page: Int): List<TopLevelRoute> = when (page) {
    PAGE_MOVIES -> movieRoutes
    PAGE_TV_SERIES -> tvSeriesRoutes
    PAGE_CELEBRITIES -> celebrityRoutes
    PAGE_FAVORITES -> favoriteRoutes
    else -> emptyList()
}
