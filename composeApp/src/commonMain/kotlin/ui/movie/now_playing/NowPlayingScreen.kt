package ui.movie.now_playing

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
fun NowPlayingScreen(
    navigator: Navigator,
    viewModel: NowPlayingViewModel = viewModel { NowPlayingViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchNowPlayingMovie(1)
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage){
        uiState.movieList?.let {
            Movies(it) { movieId ->
                navigator.navigate(NavigationScreen.MovieDetail.route.plus("/$movieId"))
            }
        }
    }
}


