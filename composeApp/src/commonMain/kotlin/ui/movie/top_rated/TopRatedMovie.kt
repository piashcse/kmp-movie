package ui.movie.top_rated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import component.Movies
import component.base.BaseColumn

@Composable
fun TopRatedMovie(
    navigator: Navigator, viewModel: TopRatedViewModel = viewModel { TopRatedViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTopRatedMovie(1)
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onDismissError = {}) {
        uiState.movieList?.let {
            Movies(it) { movieId ->
                navigator.navigate(NavigationScreen.MovieDetail.route.plus("/$movieId"))
            }
        }
    }

}