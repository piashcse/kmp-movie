import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import navigation.NavigationScreen
import navigation.currentRoute
import ui.component.AppBarWithArrow
import utils.AppString

@Composable
internal fun App() {
    val navigator = rememberNavigator()
    MaterialTheme {
        Scaffold(topBar = {
            AppBarWithArrow(
              AppString.APP_TITLE, isBackEnable = isBackButtonEnable(navigator)
            ) {
                navigator.goBack()
            }
        }, bottomBar = {
            when (currentRoute(navigator)) {
                NavigationScreen.Home.route, NavigationScreen.Popular.route, NavigationScreen.TopRated.route, NavigationScreen.Upcoming.route -> {
                    BottomNavigationUI(navigator)
                }
            }
        }) {
            Navigation(navigator)
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