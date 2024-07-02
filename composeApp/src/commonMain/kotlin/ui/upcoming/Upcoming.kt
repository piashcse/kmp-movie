package ui.upcoming

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import data.model.MovieItem
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.MovieList
import ui.component.ProgressIndicator
import utils.network.UiState

@Composable
fun Upcoming(
    navigator: Navigator,
    viewModel: UpcomingViewModel = viewModel { UpcomingViewModel() }
) {
    var isLoading by remember { mutableStateOf(false) }
    var movies by remember { mutableStateOf<List<MovieItem>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.upComing(1)
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        movies?.let {
            MovieList(it) { movieId ->
                navigator.navigate(NavigationScreen.MovieDetail.route.plus("/$movieId"))
            }
        }
        if (isLoading) {
            ProgressIndicator()
        }
    }
    viewModel.upComingMovieResponse.collectAsState().value.let {
        when (it) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success<List<MovieItem>> -> {
                movies = it.data
                isLoading = false
            }

            is UiState.Error -> {
                isLoading = false
            }
        }
    }
}