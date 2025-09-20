package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
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
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.celebrities
import kmp_movie.composeapp.generated.resources.movies
import kmp_movie.composeapp.generated.resources.tv_series
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import navigation.NavigationScreen
import navigation.currentRoute
import navigation.isBottomBarVisible
import navigation.navigationTitle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import theme.FloatingActionBackground
import ui.component.AppBarWithArrow
import ui.component.KMPNavigationSuiteScaffold
import ui.component.SearchBar
import ui.component.SearchResults
import ui.screens.AppViewModel
import utils.Platform
import utils.getPlatform

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun App(
    appViewModel: AppViewModel = koinViewModel(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    PreComposeApp {
        val navigator = rememberNavigator()
        var isAppBarVisible by remember { mutableStateOf(true) }
        val isLoading by appViewModel.isLoading.collectAsState()
        var searchFilter by remember { mutableStateOf("Movies") }
        val currentRoute = currentRoute(navigator)
        val (pagerState, items) = rememberPagerStateAndItems()
        
        MaterialTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .run {
                        if (getPlatform() == Platform.ANDROID) {
                            windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
                        } else {
                            this
                        }
                    }
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
                            onAppBarVisibilityChange = { isAppBarVisible = it },
                            onSearchFilterChange = { searchFilter = it },
                            searchFilter
                        )
                    }
                } else {
                    DestinationScaffold(
                        navigator = navigator,
                        appViewModel = appViewModel,
                        isAppBarVisible = isAppBarVisible,
                        isLoading = isLoading,
                        pagerState = pagerState,
                        onAppBarVisibilityChange = { isAppBarVisible = it },
                        onSearchFilterChange = { searchFilter = it },
                        searchFilter,
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
    onSearchFilterChange: (String) -> Unit,
    searchFilter: String,
) {
    Scaffold(
        floatingActionButton = {
            val route = currentRoute(navigator)
            if (!route.isDetailRoute()) {
                FloatingActionButton(
                    onClick = {
                        onAppBarVisibilityChange(false)
                        // Reset filter to default when opening search
                        onSearchFilterChange("Movies")
                    },
                    containerColor = FloatingActionBackground
                ) {
                    Icon(Icons.Filled.Search, "", tint = Color.White)
                }
            }
        }
    ) { contentPadding ->
        TabScreen(navigator, pagerState, contentPadding)
        if (currentRoute(navigator) != NavigationScreen.MovieDetail.route) {
            Column {
                if (!isAppBarVisible) {
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
                        onFilterChange = { onSearchFilterChange(it) }
                    ) {
                        onAppBarVisibilityChange(true)
                    }
                }
            }
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
        if (!currentRoute(navigator).isDetailRoute()) {
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

@Composable
fun isBackButtonEnable(navigator: Navigator): Boolean {
    val route = currentRoute(navigator)
    return route == NavigationScreen.ArtistDetail.route || 
           route == NavigationScreen.MovieDetail.route || 
           route == NavigationScreen.TvSeriesDetail.route
}

private fun String?.isDetailRoute(): Boolean {
    return this == NavigationScreen.MovieDetail.route || 
           this == NavigationScreen.ArtistDetail.route || 
           this == NavigationScreen.TvSeriesDetail.route
}

@Composable
private fun rememberPagerStateAndItems(): Pair<PagerState, List<NavigationScreen>> {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val items = when (pagerState.currentPage) {
        0 -> listOf(
            NavigationScreen.NowPlayingMovieNav,
            NavigationScreen.PopularMovieNav,
            NavigationScreen.TopRatedMovieNav,
            NavigationScreen.UpcomingMovieNav,
        )
        1 -> listOf(
            NavigationScreen.AiringTodayTvSeriesNav,
            NavigationScreen.OnTheAirTvSeriesNav,
            NavigationScreen.PopularTvSeriesNav,
            NavigationScreen.TopRatedTvSeriesNav,
        )
        else -> listOf(
            NavigationScreen.PopularCelebrityNav,
            NavigationScreen.TrendingCelebrityNav,
        )
    }
    return pagerState to items
}