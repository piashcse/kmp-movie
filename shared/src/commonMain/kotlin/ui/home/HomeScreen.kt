package ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.model.MovieItem
import moe.tlaster.precompose.navigation.Navigator
import ui.component.MovieList
import ui.component.ProgressIndicator
import utils.network.DataState

@Composable
internal fun HomeScreen(
    navigator: Navigator,
    viewModel: NowPlayingViewModel = NowPlayingViewModel()
) {
    LaunchedEffect(key1 = 1) {
        viewModel.nowPlayingView(1)
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        viewModel.nowPlayingResponse.collectAsState().value?.let {
            when (it) {
                is DataState.Loading -> {
                    ProgressIndicator()
                }

                is DataState.Success<List<MovieItem>> -> {
                    MovieList(it.data) { movieId ->
                        navigator.navigate("/detail/$movieId")
                    }
                }

                else -> {

                }
            }
        }
    }
}


