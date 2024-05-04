package navigation

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.AppString

sealed class NavigationScreen(
    val route: String,
    val title: String = AppString.APP_TITLE,
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.Home, contentDescription = "home"
        )
    },
    val objectName: String = "",
    val objectPath: String = ""
) {
    data object Home : NavigationScreen("home_screen")
    data object Popular : NavigationScreen("popular_screen")
    data object TopRated : NavigationScreen("top_rated_screen")
    data object Upcoming : NavigationScreen("upcoming_screen")
    data object MovieDetail :
        NavigationScreen("movie_detail_screen", objectName = "id", objectPath = "/{id}")

    data object HomeNav : NavigationScreen("home_screen", title = "Home", navIcon = {
        Icon(
            Icons.Filled.Home,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object PopularNav : NavigationScreen("popular_screen", title = "Popular", navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object TopRatedNav : NavigationScreen("top_rated_screen", title = "Top rated", navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object UpcomingNav : NavigationScreen("upcoming_screen", title = "Upcoming", navIcon = {
        Icon(
            Icons.Filled.KeyboardArrowDown,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
}