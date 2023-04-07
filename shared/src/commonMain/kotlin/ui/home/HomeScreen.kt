package ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.remote.model.MovieItem
import ui.component.MovieList
import ui.component.ProgressIndicator
import utils.network.DataState

@Composable
internal fun HomeScreen(viewModel: NowPlayingViewModel) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        viewModel.nowPlayingResponse.collectAsState().value?.let {
            when (it) {
                is DataState.Loading -> {
                    ProgressIndicator()
                }
                is DataState.Success<List<MovieItem>> -> {
                    MovieList(it.data)
                }
                else -> {

                }
            }
        }
    }
}


