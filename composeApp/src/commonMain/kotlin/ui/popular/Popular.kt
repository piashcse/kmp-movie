package ui.popular

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
fun Popular(
    navigator: Navigator,
    viewModel: PopularViewModel = viewModel { PopularViewModel() }
) {
    var isLoading by remember { mutableStateOf(false) }
    val movies = remember { mutableStateListOf<MovieItem>() }

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
    viewModel.popularMovieResponse.collectAsState().value.let {
        when (it) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success<List<MovieItem>> -> {
                movies.clear()
                movies.addAll(it.data)
                isLoading = false
            }

            is UiState.Error -> {
                isLoading = false
            }
        }
    }
}