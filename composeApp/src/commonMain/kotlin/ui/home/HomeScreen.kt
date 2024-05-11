package ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.model.MovieItem
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.MovieList
import ui.component.ProgressIndicator
import utils.AppString
import utils.network.DataState

@Composable
fun HomeScreen(
    navigator: Navigator,
    viewModel: NowPlayingViewModel = NowPlayingViewModel()
) {
    val isLoading = remember { mutableStateOf(false) }
    val movies = remember { mutableStateListOf<MovieItem>() }

    LaunchedEffect(viewModel) {
        viewModel.nowPlaying(1)
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        MovieList(movies) { movieId ->
            navigator.navigate(NavigationScreen.MovieDetail.route.plus("/$movieId"))
        }
        if (isLoading.value) {
            ProgressIndicator()
        }
    }
    viewModel.nowPlayingResponse.collectAsState().value.let {
        when (it) {
            is DataState.Loading -> {
                isLoading.value = true
            }

            is DataState.Success<List<MovieItem>> -> {
                movies.addAll(it.data)
                isLoading.value = false
            }

            is DataState.Error -> {
                isLoading.value = false
            }
        }
    }
}


