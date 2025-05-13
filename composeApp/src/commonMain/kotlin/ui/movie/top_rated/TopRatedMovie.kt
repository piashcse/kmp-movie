package ui.movie.top_rated

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import component.Movies
import component.base.BaseColumn
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import utils.OnGridPagination

@Composable
fun TopRatedMovie(
    navigator: Navigator,
    viewModel: TopRatedViewModel = viewModel { TopRatedViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit){
        viewModel.loadTopRatedMovies()
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        uiState.movieList?.let {
            Movies(it, gridState) { movieId ->
                navigator.navigate(NavigationScreen.MovieDetail.route + "/$movieId")
            }
            OnGridPagination(gridState = gridState) {
                viewModel.loadTopRatedMovies()
            }
        }
    }
}