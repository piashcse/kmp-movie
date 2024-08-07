import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.app_title
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun App(appViewModel: AppViewModel = viewModel { AppViewModel() }) {
    PreComposeApp {
        val navigator = rememberNavigator()
        val isAppBarVisible = remember { mutableStateOf(true) }
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
                        stringResource(Res.string.app_title),  isBackEnable = isBackButtonEnable(navigator)
                    ) {
                        navigator.popBackStack()
                    }
                }
            }, floatingActionButton = {
                when (currentRoute(navigator)) {
                    NavigationScreen.Home.route, NavigationScreen.Popular.route, NavigationScreen.TopRated.route, NavigationScreen.Upcoming.route -> {
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
                    when (currentRoute(navigator)) {
                        NavigationScreen.Home.route, NavigationScreen.Popular.route, NavigationScreen.TopRated.route, NavigationScreen.Upcoming.route -> {
                            BottomNavigationUI(navigator)
                        }
                    }
                }
            }) {
                if (isCompactSize()) {
                    Navigation(navigator)
                } else {
                    NavigationRailUI(navigator)
                }
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
fun BottomNavigationUI(navigator: Navigator) {
    BottomNavigation {
        val items = listOf(
            NavigationScreen.HomeNav,
            NavigationScreen.PopularNav,
            NavigationScreen.TopRatedNav,
            NavigationScreen.UpcomingNav,
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
fun NavigationRailUI(navigator: Navigator) {
    Row {
        NavigationRail {
            val items = listOf(
                NavigationScreen.HomeNav,
                NavigationScreen.PopularNav,
                NavigationScreen.TopRatedNav,
                NavigationScreen.UpcomingNav,
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
        Navigation(navigator)
    }
}

@Composable
fun isBackButtonEnable(navigator: Navigator): Boolean {
    return when (currentRoute(navigator)) {
        NavigationScreen.Home.route, NavigationScreen.Popular.route, NavigationScreen.TopRated.route, NavigationScreen.Upcoming.route -> {
            false
        }

        else -> {
            true
        }
    }
}