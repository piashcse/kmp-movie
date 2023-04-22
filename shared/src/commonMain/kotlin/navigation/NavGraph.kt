package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import ui.popular.Popular
import ui.detail.MovieDetail
import ui.home.HomeScreen
import ui.toprated.TopRated
import ui.upcoming.Upcoming

@Composable
fun Navigation(navigator: Navigator) {
    NavHost(
        navigator = navigator, initialRoute = NavigationScreen.Home.route
    ) {
        scene(route = NavigationScreen.Home.route) {
            HomeScreen(navigator)
        }
        scene(route = NavigationScreen.Popular.route) {
            Popular(navigator)
        }
        scene(route = NavigationScreen.TopRated.route) {
            TopRated(navigator)
        }
        scene(route = NavigationScreen.Upcoming.route) {
            Upcoming(navigator)
        }
        scene(route = NavigationScreen.MovieDetail.route.plus(NavigationScreen.MovieDetail.objectPath)) { backStackEntry ->
            val id: Int? = backStackEntry.path<Int>(NavigationScreen.MovieDetail.objectName)
            id?.let {
                MovieDetail(navigator, it)
            }
        }
    }
}

@Composable
fun currentRoute(navigator: Navigator): String? {
    return navigator.currentEntry.collectAsState(null).value?.route?.route

}