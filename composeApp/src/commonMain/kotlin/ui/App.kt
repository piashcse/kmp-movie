package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ui.component.AppBarWithArrow
import ui.component.KMPNavigationSuiteScaffold
import ui.component.ProgressIndicator
import ui.component.SearchBar
import ui.component.SearchForMovie
import ui.component.SearchForTVSeries
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.celebrities
import kmp_movie.composeapp.generated.resources.movies
import kmp_movie.composeapp.generated.resources.tv_series
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import navigation.NavigationScreen
import navigation.currentRoute
import navigation.isBottomBarVisible
import navigation.navigationTitle
import org.jetbrains.compose.resources.stringResource
import theme.FloatingActionBackground
import ui.screens.AppViewModel

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun App(
    appViewModel: AppViewModel = viewModel { AppViewModel() },
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    PreComposeApp {
        val navigator = rememberNavigator()
        var isAppBarVisible by remember { mutableStateOf(true) }
        val isLoading by appViewModel.isLoading.collectAsState()
        val pagerState = rememberPagerState {
            3
        }
        val items = if (pagerState.currentPage == 0) {
            listOf(
                NavigationScreen.NowPlayingMovieNav,
                NavigationScreen.PopularMovieNav,
                NavigationScreen.TopRatedMovieNav,
                NavigationScreen.UpcomingMovieNav,
            )
        } else if (pagerState.currentPage == 1) {
            listOf(
                NavigationScreen.AiringTodayTvSeriesNav,
                NavigationScreen.OnTheAirTvSeriesNav,
                NavigationScreen.PopularTvSeriesNav,
                NavigationScreen.TopRatedTvSeriesNav,
            )
        } else {
            listOf(
                NavigationScreen.PopularCelebrityNav,
                NavigationScreen.TrendingCelebrityNav,
            )
        }

        BackHandler(isAppBarVisible.not()) {
            isAppBarVisible = true
        }
        val currentRoute = currentRoute(navigator)
        MaterialTheme {
            Column(
                modifier = Modifier.consumeWindowInsets(
                    if (isAppBarVisible) {
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                    } else {
                        WindowInsets(0, 0, 0, 0)
                    },
                ),
            ) {
                if (isAppBarVisible.not()) {
                    SearchBar(appViewModel, pagerState) {
                        isAppBarVisible = true
                    }

                } else {
                    AppBarWithArrow(
                        navigationTitle(navigator),
                        isBackEnable = isBackButtonEnable(navigator)
                    ) {
                        navigator.popBackStack()
                    }
                }
                if (isBottomBarVisible(navigator)) {
                    KMPNavigationSuiteScaffold(
                        navigationSuiteItems = {
                            items.forEach { destination ->
                                item(
                                    selected = destination.route == currentRoute,
                                    onClick = {
                                        navigator.navigate(
                                            destination.route,
                                            NavOptions(launchSingleTop = true),
                                        )
                                    },
                                    icon = destination.navIcon,
                                    label = { Text(text = destination.title, fontSize = 12.sp) },
                                )
                            }
                        },
                        windowAdaptiveInfo = windowAdaptiveInfo,
                    ) {
                        DestinationScaffold(
                            navigator = navigator,
                            appViewModel = appViewModel,
                            isAppBarVisible = isAppBarVisible,
                            isLoading = isLoading,
                            pagerState = pagerState,
                            onAppBarVisibilityChange = { isAppBarVisible = it }
                        )
                    }
                } else {
                    DestinationScaffold(
                        navigator = navigator,
                        appViewModel = appViewModel,
                        isAppBarVisible = isAppBarVisible,
                        isLoading = isLoading,
                        pagerState = pagerState,
                        onAppBarVisibilityChange = { isAppBarVisible = it }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun DestinationScaffold(
    navigator: Navigator,
    appViewModel: AppViewModel,
    isAppBarVisible: Boolean,
    isLoading: Boolean,
    pagerState: PagerState,
    onAppBarVisibilityChange: (Boolean) -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            val route = currentRoute(navigator)
            when {
                route != NavigationScreen.MovieDetail.route && route != NavigationScreen.ArtistDetail.route && route != NavigationScreen.TvSeriesDetail.route -> {
                    FloatingActionButton(
                        onClick = { onAppBarVisibilityChange(false) },
                        backgroundColor = FloatingActionBackground
                    ) {
                        Icon(Icons.Filled.Search, "", tint = Color.White)
                    }
                }
            }
        }
    ) { padding ->
        TabScreen(navigator, pagerState, padding)
        if (currentRoute(navigator) !== NavigationScreen.MovieDetail.route) {
            Column {
                if (isAppBarVisible.not()) {
                    if (pagerState.currentPage == 0) {
                        SearchForMovie(navigator, appViewModel.movieSearchData.value) {
                            onAppBarVisibilityChange(true)
                        }
                    } else {
                        SearchForTVSeries(
                            navigator,
                            appViewModel.tvSeriesSearchData.value
                        ) {
                            onAppBarVisibilityChange(true)
                        }
                    }
                    ProgressIndicator(isLoading)
                }
            }
        }
    }
}

@Composable
fun isBackButtonEnable(navigator: Navigator): Boolean {
    return when (currentRoute(navigator)) {
        NavigationScreen.ArtistDetail.route, NavigationScreen.MovieDetail.route, NavigationScreen.TvSeriesDetail.route -> {
            true
        }

        else -> {
            false
        }
    }
}

@Composable
fun TabScreen(navigator: Navigator, pagerState: PagerState, padding: PaddingValues) {
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
        if (currentRoute(navigator) !in listOf(
                NavigationScreen.MovieDetail.route,
                NavigationScreen.TvSeriesDetail.route,
                NavigationScreen.ArtistDetail.route
            )
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.PrimaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colors.primary,
                    )
                }
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
                                color = if (pagerState.currentPage == index) MaterialTheme.colors.primary else Color.Gray
                            )
                        })
                }
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            Navigation(navigator, pagerState.currentPage)
        }
    }
}
