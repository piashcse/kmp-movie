package navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import ui.detail.MovieDetail
import ui.home.HomeScreen

@Composable
fun Navigation(navigator: Navigator){
    NavHost(
        navigator = navigator, initialRoute = "/home"
    ) {
        scene(route = "/home") {
            HomeScreen(navigator)
        }
        scene(route = "/detail/{id}",) { backStackEntry ->
            val id: Int? = backStackEntry.path<Int>("id")
            id?.let {
                MovieDetail(navigator, it)
            }

        }
    }
}