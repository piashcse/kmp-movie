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
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.PrimaryTabRow
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
import ui.component.SearchResults
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
                if (isAppBarVisible) {
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
    var searchFilter by remember { mutableStateOf("Movies") }
    
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            val route = currentRoute(navigator)
            when {
                route != NavigationScreen.MovieDetail.route && route != NavigationScreen.ArtistDetail.route && route != NavigationScreen.TvSeriesDetail.route -> {
                    FloatingActionButton(
                        onClick = { 
                            onAppBarVisibilityChange(false)
                            // Reset filter to default when opening search
                            searchFilter = "Movies"
                        },
                        containerColor = FloatingActionBackground
                    ) {
                        Icon(Icons.Filled.Search, "", tint = Color.White)
                    }
                }
            }
        }
    ) { contentPadding ->
        TabScreen(navigator, pagerState, contentPadding)
        if (currentRoute(navigator) !== NavigationScreen.MovieDetail.route) {
            Column {
                if (isAppBarVisible.not()) {
                    SearchBar(
                        viewModel = appViewModel,
                        searchFilter = searchFilter,
                        isLoading = isLoading,
                        pressOnBack = {
                            onAppBarVisibilityChange(true)
                        }
                    )
                    // Display search results based on the active filter
                    SearchResults(
                        navController = navigator,
                        movieSearchData = appViewModel.movieSearchData.value,
                        tvSeriesSearchData = appViewModel.tvSeriesSearchData.value,
                        celebritySearchData = appViewModel.celebritySearchData.value,
                        currentFilter = searchFilter,
                        onFilterChange = { filter -> 
                            searchFilter = filter
                            // Clear previous search results when changing filter
                            // The actual API call will be triggered when the user types
                        }
                    ) {
                        onAppBarVisibilityChange(true)
                    }
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
@OptIn(ExperimentalMaterial3Api::class)
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
        }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            Navigation(navigator, pagerState.currentPage)
        }
    }
}
