package ui.screens.movie.upcoming

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
fun UpcomingMovie(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: UpcomingMovieViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.loadUpcomingMovies()
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
                viewModel.loadUpcomingMovies()
            }
        }
    }
}