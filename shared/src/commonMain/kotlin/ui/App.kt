package ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import org.jetbrains.compose.resources.StringResource
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
private const val TOP_LEVEL_STATE_KEY = "top_level"

private val pageTabs = listOf(
    PageTab(Res.string.movies, Icons.Filled.PlayArrow),
    PageTab(Res.string.tv_series, Icons.AutoMirrored.Filled.List),
    PageTab(Res.string.celebrities, Icons.Filled.Star),
    PageTab(Res.string.favorites, Icons.Filled.Favorite),
)

private data class PageTab(
    val titleRes: StringResource,
    val icon: ImageVector,
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
            // Keeps list state (scroll position, selected tab) while a detail screen is open.
            val saveableStateHolder = rememberSaveableStateHolder()

            val backState = rememberNavigationEventState(NavigationEventInfo.None)
            NavigationEventHandler(
                state = backState,
                isBackEnabled = navigator.canGoBack,
                onBackCompleted = { navigator.popBackStack() },
            )

            // Remember the selected sub-tab of each top-level page.
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

            LaunchedEffect(navigator.currentRoute) {
                val route = navigator.currentRoute
                if (route is TopLevelRoute) {
                    val page = getPageForRoute(route)
                    val index = getItemsForPage(page).indexOf(route)
                    if (index >= 0) selectIndex(page, index)
                }
            }

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
                val currentRoute = navigator.currentTopLevelRoute
                val currentPage = getPageForRoute(currentRoute)
                val currentItems = getItemsForPage(currentPage)

                KMPNavigationSuiteScaffold(
                    windowAdaptiveInfo = windowAdaptiveInfo,
                    navigationSuiteItems = {
                        pageTabs.forEachIndexed { pageIndex, tab ->
                            item(
                                selected = currentPage == pageIndex,
                                onClick = {
                                    val target = routeForPage(pageIndex)
                                    if (navigator.currentRoute != target) {
                                        navigator.navigateToTopLevel(target)
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = stringResource(tab.titleRes),
                                    )
                                },
                                label = { Text(text = stringResource(tab.titleRes)) },
                            )
                        }
                    },
                ) {
                    saveableStateHolder.SaveableStateProvider(TOP_LEVEL_STATE_KEY) {
                        TopLevelScaffold(
                            page = currentPage,
                            route = currentRoute,
                            items = currentItems,
                            selectedIndex = selectedIndex(currentPage),
                            onSelectTab = { index ->
                                if (index != selectedIndex(currentPage)) {
                                    selectIndex(currentPage, index)
                                    navigator.navigateToTopLevel(currentItems[index])
                                }
                            },
                            onOpenSearch = { navigator.navigate(Search) },
                            onNavigate = { navigator.navigate(it) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopLevelScaffold(
    page: Int,
    route: TopLevelRoute,
    items: List<TopLevelRoute>,
    selectedIndex: Int,
    onSelectTab: (Int) -> Unit,
    onOpenSearch: () -> Unit,
    onNavigate: (Route) -> Unit,
    saveableStateHolder: SaveableStateHolder,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(pageTabs[page].titleRes)) },
                actions = { ThemeModeToggle() },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onOpenSearch) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(Res.string.action_search),
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            if (items.size > 1) {
                PrimaryTabRow(selectedTabIndex = selectedIndex) {
                    items.forEachIndexed { index, item ->
                        Tab(
                            selected = index == selectedIndex,
                            onClick = { onSelectTab(index) },
                            text = { Text(text = stringResource(item.titleRes)) },
                        )
                    }
                }
            }

            Crossfade(
                targetState = route,
                animationSpec = tween(180),
                label = "page_route_transition",
                modifier = Modifier.weight(1f),
            ) { targetRoute ->
                saveableStateHolder.SaveableStateProvider(targetRoute.toString()) {
                    TopLevelContent(route = targetRoute, onNavigate = onNavigate)
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
