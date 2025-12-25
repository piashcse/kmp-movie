package ui.screens.movie.top_rated

import androidx.compose.runtime.Composable
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen

@Composable
fun TopRatedMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TopRatedMovieViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadTopRatedMovies() },
        getItems = { it.movieList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}