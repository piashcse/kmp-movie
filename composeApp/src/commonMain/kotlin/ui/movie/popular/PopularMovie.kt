package ui.movie.popular

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
fun PopularMovie(
    navigator: Navigator,
    viewModel: PopularMovieViewModel = viewModel { PopularMovieViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit){
        viewModel.loadPopularMovies()
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
                viewModel.loadPopularMovies()
            }
        }
    }
}