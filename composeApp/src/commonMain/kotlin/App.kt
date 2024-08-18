import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.app_title
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
import ui.component.SearchUI
import utils.isCompactSize

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun App(appViewModel: AppViewModel = viewModel { AppViewModel() }) {
    PreComposeApp {
        val navigator = rememberNavigator()
        val isAppBarVisible = remember { mutableStateOf(true) }
        val pagerState = rememberPagerState {
            2
        }
        val isLoading by appViewModel.isLoading.collectAsState()

        BackHandler(isAppBarVisible.value.not()) {
            isAppBarVisible.value = true
        }
        MaterialTheme {
            Scaffold(topBar = {
                if (isAppBarVisible.value.not()) {
                    SearchBar(appViewModel) {
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
                when (currentRoute(navigator)) {
                    NavigationScreen.NowPlayingMovie.route, NavigationScreen.PopularMovie.route, NavigationScreen.TopRatedMovie.route, NavigationScreen.UpcomingMovie.route -> {
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
                if (pagerState.currentPage == 0) {
                    if (isCompactSize()) {
                        BottomNavigationMovies(navigator)
                    }
                } else {
                    if (isCompactSize()) {
                        BottomNavigationTvSeries(navigator)
                    }
                }

            }) {
                TabScreen(navigator, pagerState)
                if (currentRoute(navigator) !== NavigationScreen.MovieDetail.route) {
                    Column {
                        if (isAppBarVisible.value.not()) {
                            SearchUI(navigator, appViewModel.searchData.value) {
                                isAppBarVisible.value = true
                            }
                            ProgressIndicator(isLoading)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationMovies(navigator: Navigator) {
    BottomNavigation {
        val items = listOf(
            NavigationScreen.NowPlayingMovieNav,
            NavigationScreen.PopularMovieNav,
            NavigationScreen.TopRatedMovieNav,
            NavigationScreen.UpcomingMovieNav,
        )
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
fun BottomNavigationTvSeries(navigator: Navigator) {
    BottomNavigation {
        val items = listOf(
            NavigationScreen.AiringTodayTvSeriesNav,
            NavigationScreen.OnTheAirTvSeriesNav,
            NavigationScreen.PopularTvSeriesNav,
            NavigationScreen.TopRatedTvSeriesNav,
        )
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationRailUI(navigator: Navigator, pagerState: PagerState) {
    Row {
        NavigationRail {
            val items = listOf(
                NavigationScreen.NowPlayingMovieNav,
                NavigationScreen.PopularMovieNav,
                NavigationScreen.TopRatedMovieNav,
                NavigationScreen.UpcomingMovieNav,
            )
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
        Navigation(navigator, pagerState)
    }
}

@Composable
fun isBackButtonEnable(navigator: Navigator): Boolean {
    return when (currentRoute(navigator)) {
        NavigationScreen.ArtistDetail.route, NavigationScreen.MovieDetail.route-> {
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
    val tabs = listOf("Movies", "TV Series")

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = pagerState.currentPage == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = { Text(title) })
            }
        }
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> Content(navigator, pagerState)
                1 -> Content(navigator, pagerState)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(navigator: Navigator, pagerState: PagerState) {
    if (isCompactSize()) {
        Navigation(navigator, pagerState)
    } else {
        NavigationRailUI(navigator, pagerState)
    }
}
