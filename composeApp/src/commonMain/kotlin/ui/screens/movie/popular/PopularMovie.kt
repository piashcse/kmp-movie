package ui.screens.movie.popular

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import ui.component.Movies
import ui.component.base.BaseColumn
import utils.OnGridPagination

@Composable
fun PopularMovie(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PopularMovieViewModel = koinViewModel()
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
                onNavigateToDetail(movieId)
            }
            OnGridPagination(gridState = gridState) {
                viewModel.loadPopularMovies()
            }
        }
    }
}