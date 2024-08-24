package ui.movie.popular

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.MovieList
import ui.component.ProgressIndicator

@Composable
fun PopularMovie(
    navigator: Navigator, viewModel: PopularMovieViewModel = viewModel { PopularMovieViewModel() }
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val movies by viewModel.popularMovieResponse.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.popularMovie(1)
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        MovieList(movies.toList()) { movieId ->
            navigator.navigate(NavigationScreen.MovieDetail.route.plus("/$movieId"))
        }
        if (isLoading) {
            ProgressIndicator()
        }
    }
}