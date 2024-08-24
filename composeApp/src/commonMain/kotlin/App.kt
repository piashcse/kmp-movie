import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.app_title
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
import org.jetbrains.compose.resources.stringResource
import theme.FloatingActionBackground
import ui.AppViewModel
import ui.component.AppBarWithArrow
import ui.component.ProgressIndicator
import ui.component.SearchBar
import ui.component.SearchForMovie
import ui.component.SearchForTVSeries
import utils.isCompactSize

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun App(appViewModel: AppViewModel = viewModel { AppViewModel() }) {
    PreComposeApp {
        val navigator = rememberNavigator()
        val isAppBarVisible = remember { mutableStateOf(true) }
        val isLoading by appViewModel.isLoading.collectAsState()
        val pagerState = rememberPagerState {
            2
        }

        BackHandler(isAppBarVisible.value.not()) {
            isAppBarVisible.value = true
        }
        MaterialTheme {
            Scaffold(topBar = {
                if (isAppBarVisible.value.not()) {
                    SearchBar(appViewModel, pagerState) {
                        isAppBarVisible.value = true
                    }

                } else {
                    AppBarWithArrow(
                        stringResource(Res.string.app_title),
                        isBackEnable = isBackButtonEnable(navigator)
                    ) {
                        navigator.popBackStack()
                    }
                }
            }, floatingActionButton = {
                val route = currentRoute(navigator)
                when {
                    route != NavigationScreen.MovieDetail.route && route != NavigationScreen.ArtistDetail.route && route != NavigationScreen.TvSeriesDetail.route -> {
                        FloatingActionButton(
                            onClick = {
                                isAppBarVisible.value = false
                            }, backgroundColor = FloatingActionBackground
                        ) {
                            Icon(Icons.Filled.Search, "", tint = Color.White)
                        }
                    }
                }
            }, bottomBar = {
                if (isCompactSize()) {
                    BottomNavigation(navigator, pagerState)
                }
            }) {
                TabScreen(navigator, pagerState)
                if (currentRoute(navigator) !== NavigationScreen.MovieDetail.route) {
                    Column {
                        if (isAppBarVisible.value.not()) {
                            if (pagerState.currentPage == 0) {
                                SearchForMovie(navigator, appViewModel.movieSearchData.value) {
                                    isAppBarVisible.value = true
                                }
                            } else {
                                SearchForTVSeries(
                                    navigator,
                                    appViewModel.tvSeriesSearchData.value
                                ) {
                                    isAppBarVisible.value = true
                                }
                            }
                            ProgressIndicator(isLoading)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigation(navigator: Navigator, pagerState: PagerState) {
    BottomNavigation {
        val items = if (pagerState.currentPage == 0) {
            listOf(
                NavigationScreen.NowPlayingMovieNav,
                NavigationScreen.PopularMovieNav,
                NavigationScreen.TopRatedMovieNav,
                NavigationScreen.UpcomingMovieNav,
            )
        } else {
            listOf(
                NavigationScreen.AiringTodayTvSeriesNav,
                NavigationScreen.OnTheAirTvSeriesNav,
                NavigationScreen.PopularTvSeriesNav,
                NavigationScreen.TopRatedTvSeriesNav,
            )
        }
        items.forEach {
            BottomNavigationItem(label = { Text(text = it.title) },
                selected = it.route == currentRoute(navigator),
                icon = it.navIcon,
                onClick = {
                    navigator.navigate(
                        it.route,
                        NavOptions(
                            launchSingleTop = true,
                        ),
                    )
                })
        }
    }
}


@Composable
fun NavigationRail(navigator: Navigator, page: Int) {
    Row {
        NavigationRail {
            val items = if (page == 0) {
                listOf(
                    NavigationScreen.NowPlayingMovieNav,
                    NavigationScreen.PopularMovieNav,
                    NavigationScreen.TopRatedMovieNav,
                    NavigationScreen.UpcomingMovieNav,
                )
            } else {
                listOf(
                    NavigationScreen.AiringTodayTvSeriesNav,
                    NavigationScreen.OnTheAirTvSeriesNav,
                    NavigationScreen.PopularTvSeriesNav,
                    NavigationScreen.TopRatedTvSeriesNav,
                )
            }
            items.forEach {
                NavigationRailItem(label = { Text(text = it.title, fontSize = 12.sp) },
                    selected = it.route == currentRoute(navigator),
                    icon = it.navIcon,
                    onClick = {
                        navigator.navigate(
                            it.route,
                            NavOptions(
                                launchSingleTop = true,
                            ),
                        )
                    })
            }
        }
        Navigation(navigator, page)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen(navigator: Navigator, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(stringResource(Res.string.movies), stringResource(Res.string.tv_series))

    Column(Modifier.padding(bottom = 56.dp)) {
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
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize()
        ) { page ->
            if (isCompactSize()) {
                Navigation(navigator, page)
            } else {
                NavigationRail(navigator, page)
            }
        }
    }
}
