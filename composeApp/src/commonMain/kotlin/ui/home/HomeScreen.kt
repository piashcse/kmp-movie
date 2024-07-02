package ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import data.model.MovieItem
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.MovieList
import ui.component.ProgressIndicator
import utils.network.UiState

@Composable
fun HomeScreen(
    navigator: Navigator,
    viewModel: NowPlayingViewModel = viewModel { NowPlayingViewModel() }
) {
    var isLoading by remember { mutableStateOf(false) }
    var movies by  remember { mutableStateOf<List<MovieItem>?>(null) }

    LaunchedEffect(Unit){
        viewModel.nowPlaying(1)
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
    viewModel.nowPlayingResponse.collectAsState().value.let {
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


