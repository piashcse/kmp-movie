package ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import data.model.MovieItem
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.MovieList
import ui.component.ProgressIndicator
import utils.network.DataState

@Composable
fun HomeScreen(
    navigator: Navigator,
    nowPlayingViewModel: NowPlayingViewModel = viewModel { NowPlayingViewModel() }
) {
    val isLoading = remember { mutableStateOf(false) }
    val movies = remember { mutableStateListOf<MovieItem>() }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        MovieList(movies) { movieId ->
            navigator.navigate(NavigationScreen.MovieDetail.route.plus("/$movieId"))
        }
        if (isLoading.value) {
            ProgressIndicator()
        }
    }
    nowPlayingViewModel.nowPlayingResponse.collectAsState().value.let {
        when (it) {
            is DataState.Loading -> {
                isLoading.value = true
            }

            is DataState.Success<List<MovieItem>> -> {
                movies.clear()
                movies.addAll(it.data)
                isLoading.value = false
            }

            is DataState.Error -> {
                isLoading.value = false
            }
        }
    }
}


