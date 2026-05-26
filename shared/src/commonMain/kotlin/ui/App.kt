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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import data.model.local.MediaType
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.celebrities
import kmp_movie.composeapp.generated.resources.favorites
import kmp_movie.composeapp.generated.resources.movies
import kmp_movie.composeapp.generated.resources.tv_series
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import navigation.AiringTodayTvSeries
import navigation.ArtistDetail
import navigation.FavoriteCelebrity
import navigation.FavoriteMovie
import navigation.FavoriteTvSeries
import navigation.GenreContent
import navigation.Genres
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
import ui.screens.genre.GenreContentScreen
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

private val pageTabs = listOf(
    PageTab("Movies", Res.string.movies, Icons.Filled.Movie),
    PageTab("TV Series", Res.string.tv_series, Icons.Filled.Timeline),
    PageTab("Celebrities", Res.string.celebrities, Icons.Filled.Star),
    PageTab("Favorites", Res.string.favorites, Icons.Filled.Favorite),
)

private data class PageTab(
    val title: String,
    val titleRes: org.jetbrains.compose.resources.StringResource,
    val icon: ImageVector,
)

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun App(
    appViewModel: AppViewModel = koinViewModel(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    ProvideThemeState(appViewModel = appViewModel) {
        KmpMovieTheme(darkTheme = shouldUseDarkTheme()) {
            val navigator = rememberNavigator(NowPlayingMovie)
            val isDetailScreen = navigator.isDetailScreen

            if (isDetailScreen) {
                Crossfade(
                    targetState = navigator.currentRoute,
                    animationSpec = tween(300),
                    label = "detail_transition",
                ) { currentRoute ->
                    Scaffold { paddingValues ->
                        DetailScreen(
                            navigator = navigator,
                            currentRoute = currentRoute,
                            modifier = Modifier.padding(paddingValues),
                        )
                    }
                }
            } else {
                val currentTopLevelRoute = navigator.currentTopLevelRoute
                val currentPage = getPageForRoute(currentTopLevelRoute)
                val currentItems = getItemsForPage(currentPage)

                KMPNavigationSuiteScaffold(
                    navigationSuiteItems = {
                        currentItems.forEach { item ->
                            item(
                                selected = navigator.currentRoute == item,
                                onClick = { navigator.navigateToTopLevel(item) },
                                icon = {
                                    Icon(
                                        imageVector = item.icon!!,
                                        contentDescription = item.title,
                                    )
                                },
                                label = { Text(text = item.title) },
                            )
                        }
                    }
                ) {
                    Crossfade(
                        targetState = navigator.currentRoute,
                        animationSpec = tween(200),
                        label = "content_transition",
                    ) { currentRoute ->
                        val activeRoute = currentRoute as TopLevelRoute
                        val page = getPageForRoute(activeRoute)

                        ListScaffold(
                            navigator = navigator,
                            activeRoute = activeRoute,
                            currentPage = page,
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
        is GenreContent -> GenreContentScreen(
            genreId = currentRoute.genreId,
            genreName = currentRoute.genreName,
            onBackClick = { navigator.popBackStack() },
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
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(Search) }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    ) { paddingValues ->
        MainScreen(
            navigator = navigator,
            activeRoute = activeRoute,
            currentPage = currentPage,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun MainScreen(
    navigator: navigation.Navigator,
    activeRoute: TopLevelRoute,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = currentPage) { PAGE_COUNT }

    LaunchedEffect(activeRoute) {
        val page = getPageForRoute(activeRoute)
        if (pagerState.currentPage != page) {
            pagerState.animateScrollToPage(page)
        }
    }

    LaunchedEffect(pagerState.settledPage) {
        val page = pagerState.settledPage
        if (page != getPageForRoute(activeRoute)) {
            val newRoute = getDefaultRouteForPage(page)
            if (navigator.currentRoute != newRoute) {
                navigator.navigateToTopLevel(newRoute)
            }
        }
    }

    TabScreen(
        pagerState = pagerState,
        activeRoute = activeRoute,
        onNavigate = { navigator.navigate(it) },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabScreen(
    pagerState: PagerState,
    activeRoute: TopLevelRoute,
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
                                color = if (pagerState.currentPage == index)
                                    MaterialTheme.colorScheme.primary else Color.Gray,
                            )
                        },
                    )
                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                val route = if (page == getPageForRoute(activeRoute)) activeRoute
                else getDefaultRouteForPage(page)

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
                    else -> NowPlayingScreen(
                        onNavigateToDetail = { id -> onNavigate(MovieDetail(id)) },
                    )
                }
            }
        }
    }
}

private fun getPageForRoute(route: TopLevelRoute): Int = when (route) {
    is NowPlayingMovie, is PopularMovie, is TopRatedMovie, is UpcomingMovie -> PAGE_MOVIES
    is AiringTodayTvSeries, is OnTheAirTvSeries, is PopularTvSeries, is TopRatedTvSeries -> PAGE_TV_SERIES
    is PopularCelebrity, is TrendingCelebrity -> PAGE_CELEBRITIES
    is FavoriteMovie, is FavoriteTvSeries, is FavoriteCelebrity -> PAGE_FAVORITES
    is Genres -> PAGE_MOVIES
}

private fun getDefaultRouteForPage(page: Int): TopLevelRoute = when (page) {
    PAGE_MOVIES -> NowPlayingMovie
    PAGE_TV_SERIES -> AiringTodayTvSeries
    PAGE_CELEBRITIES -> PopularCelebrity
    PAGE_FAVORITES -> FavoriteMovie
    else -> NowPlayingMovie
}

private fun getItemsForPage(page: Int): List<TopLevelRoute> = when (page) {
    PAGE_MOVIES -> listOf(NowPlayingMovie, PopularMovie, TopRatedMovie, UpcomingMovie)
    PAGE_TV_SERIES -> listOf(AiringTodayTvSeries, OnTheAirTvSeries, PopularTvSeries, TopRatedTvSeries)
    PAGE_CELEBRITIES -> listOf(PopularCelebrity, TrendingCelebrity)
    PAGE_FAVORITES -> listOf(FavoriteMovie, FavoriteTvSeries, FavoriteCelebrity)
    else -> emptyList()
}
